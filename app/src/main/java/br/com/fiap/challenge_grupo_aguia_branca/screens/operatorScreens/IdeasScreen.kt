package br.com.fiap.challenge_grupo_aguia_branca.screens.operatorScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaCinzaBorda
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaCinzaFundo
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaCinzaTexto
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaPreto
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaVerde
import br.com.fiap.challenge_grupo_aguia_branca.viewmodel.ApiViewModel

@Composable
fun IdeasScreen(
    viewModel: ApiViewModel = viewModel(),
    onNavigate: (OperadorDestination) -> Unit = {},
    onVoltarClick: () -> Unit = {}
) {
    var filtroSelecionado by remember { mutableStateOf("Todas") }
    val ideias by viewModel.registros.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.listarMinhasIdeias()
    }

    val ideiasFiltradas = when (filtroSelecionado) {
        "Aprovadas" -> ideias.filter { it.status == "APROVADA" }
        "Em Análise" -> ideias.filter { it.status == "EM_ANALISE" }
        else -> ideias
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(InovaCinzaFundo)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            InovaTopBar(
                titulo = "Minhas Ideias",
                mostrarVoltar = true,
                onVoltarClick = onVoltarClick
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 14.dp)
                    .padding(top = 14.dp, bottom = 80.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FilterChipIdeia(
                        text = "Todas",
                        selected = filtroSelecionado == "Todas",
                        onClick = { filtroSelecionado = "Todas" }
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    FilterChipIdeia(
                        text = "Aprovadas",
                        selected = filtroSelecionado == "Aprovadas",
                        onClick = { filtroSelecionado = "Aprovadas" }
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    FilterChipIdeia(
                        text = "Em Análise",
                        selected = filtroSelecionado == "Em Análise",
                        onClick = { filtroSelecionado = "Em Análise" }
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                if (ideiasFiltradas.isEmpty()) {
                    Text(
                        text = "Nenhuma ideia encontrada.",
                        color = InovaCinzaTexto,
                        fontSize = 12.sp
                    )
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(ideiasFiltradas) { ideia ->
                            IdeaListItemCard(ideia)
                        }
                    }
                }
            }
        }

        OperadorBottomBar(
            currentScreen = OperadorDestination.IDEIAS,
            onNavigate = onNavigate,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun FilterChipIdeia(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .height(34.dp)
            .background(
                color = if (selected) InovaAzulEscuro else InovaBranco,
                shape = RoundedCornerShape(18.dp)
            )
            .border(
                width = 1.dp,
                color = if (selected) InovaAzulEscuro else InovaCinzaBorda,
                shape = RoundedCornerShape(18.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 17.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (selected) InovaBranco else InovaAzulEscuro,
            fontSize = 12.sp,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

@Composable
fun IdeaListItemCard(ideia: RegistroResponse) {
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
                elevation = 2.dp,
                shape = RoundedCornerShape(8.dp),
                ambientColor = InovaPreto.copy(alpha = 0.06f),
                spotColor = InovaPreto.copy(alpha = 0.06f)
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
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = ideia.descricao ?: "Sem descrição",
                color = InovaCinzaTexto,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                ideia.categoria?.takeIf { it.isNotBlank() }?.let {
                    Box(
                        modifier = Modifier
                            .background(
                                color = InovaCinzaFundo,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = it,
                            color = InovaCinzaTexto,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))
                }

                Text(
                    text = statusLabel,
                    color = statusColor,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun IdeasScreenPreview() {
    ChallengegrupoaguiabrancaTheme {
        IdeasScreen()
    }
}
