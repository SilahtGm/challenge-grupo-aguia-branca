package br.com.fiap.challenge_grupo_aguia_branca.screens.operatorScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.fiap.challenge_grupo_aguia_branca.data.model.RegistroResponse
import br.com.fiap.challenge_grupo_aguia_branca.navigation.InovaTopBar
import br.com.fiap.challenge_grupo_aguia_branca.navigation.OperadorBottomBar
import br.com.fiap.challenge_grupo_aguia_branca.navigation.OperadorDestination
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.ChallengegrupoaguiabrancaTheme
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaAzulClaro
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaAzulEscuro
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaBranco
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaCinzaFundo
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaCinzaTexto
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaLilas
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaPreto
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaVerde
import br.com.fiap.challenge_grupo_aguia_branca.viewmodel.ApiViewModel

@Composable
fun HomeOperadorScreen(
    viewModel: ApiViewModel = viewModel(),
    onNavigate: (OperadorDestination) -> Unit = {},
    onLogout: () -> Unit = {}
) {
    val ideias by viewModel.registros.collectAsState()
    val usuario by viewModel.usuarioLogado.collectAsState()

    LaunchedEffect(usuario?.id) {
        viewModel.listarMinhasIdeias()
    }

    val ideiasEnviadas = ideias.size
    val ideiasAprovadas = ideias.count { it.status == "APROVADA" }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(InovaCinzaFundo)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            InovaTopBar(
                titulo = "Águia Branca Colab",
                mostrarLogout = true,
                onLogoutClick = onLogout
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 16.dp)
                    .padding(top = 14.dp, bottom = 80.dp)
            ) {
                WelcomeCard()

                Spacer(modifier = Modifier.height(18.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    StatusCard(
                        title = "IDEIAS ENVIADAS",
                        value = ideiasEnviadas.toString(),
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(14.dp))

                    StatusCard(
                        title = "APROVADAS",
                        value = ideiasAprovadas.toString(),
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                RegisterIdeaButton(
                    onClick = {
                        onNavigate(OperadorDestination.IDEA_REGISTER)
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Minhas Ideias Recentes",
                    color = InovaAzulEscuro,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                Spacer(modifier = Modifier.height(10.dp))

                if (ideias.isEmpty()) {
                    Text(
                        text = "Nenhuma ideia registrada ainda.",
                        color = InovaCinzaTexto,
                        fontSize = 12.sp
                    )
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(ideias.take(5)) { ideia ->
                            RecentIdeaCard(ideia)
                        }
                    }
                }
            }
        }

        OperadorBottomBar(
            currentScreen = OperadorDestination.HOME,
            onNavigate = onNavigate,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun WelcomeCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .shadow(
                elevation = 5.dp,
                shape = RoundedCornerShape(10.dp),
                ambientColor = InovaPreto.copy(alpha = 0.12f),
                spotColor = InovaPreto.copy(alpha = 0.12f)
            ),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = InovaLilas
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 18.dp, vertical = 18.dp)
        ) {
            Column {
                Text(
                    text = "Bem-vindo! 👋",
                    color = InovaAzulEscuro,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Compartilhe suas ideias e contribua para a\ninovação.",
                    color = InovaAzulEscuro,
                    fontSize = 12.sp,
                    lineHeight = 16.sp
                )
            }
        }
    }
}

@Composable
fun StatusCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(74.dp)
            .shadow(
                elevation = 3.dp,
                shape = RoundedCornerShape(8.dp),
                ambientColor = InovaPreto.copy(alpha = 0.08f),
                spotColor = InovaPreto.copy(alpha = 0.08f)
            ),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = InovaBranco
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 15.dp, top = 14.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = title,
                color = InovaCinzaTexto,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = value,
                color = InovaAzulEscuro,
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}

@Composable
fun RegisterIdeaButton(
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(53.dp)
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(8.dp),
                ambientColor = InovaPreto.copy(alpha = 0.10f),
                spotColor = InovaPreto.copy(alpha = 0.10f)
            )
            .background(
                color = InovaAzulClaro,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "💡",
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.width(11.dp))

            Text(
                text = "Registrar Nova Ideia",
                color = InovaBranco,
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}

@Composable
fun RecentIdeaCard(ideia: RegistroResponse) {
    val (statusLabel, statusColor) = when (ideia.status) {
        "APROVADA" -> "✓ Aprovada" to InovaVerde
        "REPROVADA" -> "✗ Reprovada" to InovaAzulClaro
        "EM_ANALISE" -> "⏳ Em análise" to InovaAzulEscuro
        "VIROU_PROJETO" -> "🚀 Virou projeto" to InovaAzulClaro
        else -> "• Pendente" to InovaCinzaTexto
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 3.dp,
                shape = RoundedCornerShape(8.dp),
                ambientColor = InovaPreto.copy(alpha = 0.08f),
                spotColor = InovaPreto.copy(alpha = 0.08f)
            ),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = InovaBranco
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp)
        ) {
            Text(
                text = ideia.titulo ?: ideia.nome ?: "—",
                color = InovaAzulEscuro,
                fontSize = 13.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = ideia.descricao ?: "Sem descrição",
                color = InovaCinzaTexto,
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = statusLabel,
                color = statusColor,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeOperadorScreenPreview() {
    ChallengegrupoaguiabrancaTheme {
        HomeOperadorScreen()
    }
}
