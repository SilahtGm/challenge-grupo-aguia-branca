package br.com.fiap.challenge_grupo_aguia_branca.screens.gestorScreens

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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.fiap.challenge_grupo_aguia_branca.data.remote.RetrofitClient
import br.com.fiap.challenge_grupo_aguia_branca.navigation.GestorBottomBar
import br.com.fiap.challenge_grupo_aguia_branca.navigation.GestorDestination
import br.com.fiap.challenge_grupo_aguia_branca.navigation.InovaTopBar
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.ChallengegrupoaguiabrancaTheme
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaAzulClaro
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaAzulEscuro
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaBranco
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaCinzaFundo
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaCinzaTexto
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaLilas
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaPreto
import br.com.fiap.challenge_grupo_aguia_branca.viewmodel.ApiViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeGestorScreen(
    viewModel: ApiViewModel = viewModel(),
    onNavigate: (GestorDestination) -> Unit = {},
    onLogout: () -> Unit = {}
) {
    var pendentes by remember { mutableStateOf(0) }
    var totalProjetos by remember { mutableStateOf(0) }
    val scope = rememberCoroutineScope()
    val erro by viewModel.erro.collectAsState()

    LaunchedEffect(Unit) {
        scope.launch {
            try {
                val api = RetrofitClient.apiService
                val ideias = api.listarRegistrosPorTipo("IDEIA")
                pendentes = ideias.count {
                    it.status == "PENDENTE" || it.status == "EM_ANALISE"
                }
                val projetos = api.listarRegistrosPorTipo("PROJETO")
                totalProjetos = projetos.size
            } catch (_: Exception) {
                // O ApiViewModel já reportará erros nas listagens individuais
            }
        }
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
                titulo = "INOVAGAB",
                mostrarLogout = true,
                onLogoutClick = onLogout
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp, bottom = 80.dp)
            ) {
                DashboardHeader(
                    titulo = "Dashboard Tático",
                    descricao = "Acompanhe o funil de inovação",
                    icone = "📊"
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    GestorStatCard(
                        titulo = "PENDENTES",
                        valor = pendentes.toString(),
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    GestorStatCard(
                        titulo = "PROJETOS",
                        valor = totalProjetos.toString(),
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                GestorActionButton(
                    label = "Curadoria de Ideias",
                    icon = "💡",
                    backgroundColor = InovaAzulClaro,
                    onClick = { onNavigate(GestorDestination.IDEIAS) }
                )

                Spacer(modifier = Modifier.height(12.dp))

                GestorActionButton(
                    label = "Gestão de Projetos",
                    icon = "📁",
                    backgroundColor = InovaAzulEscuro,
                    onClick = { onNavigate(GestorDestination.PROJETOS) }
                )

                erro?.let {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = it,
                        color = InovaAzulClaro,
                        fontSize = 12.sp
                    )
                }
            }
        }

        GestorBottomBar(
            currentScreen = GestorDestination.HOME,
            onNavigate = onNavigate,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun DashboardHeader(
    titulo: String,
    descricao: String,
    icone: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(12.dp),
                ambientColor = InovaPreto.copy(alpha = 0.12f),
                spotColor = InovaPreto.copy(alpha = 0.12f)
            ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = InovaLilas
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 18.dp, vertical = 18.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = titulo,
                    color = InovaAzulEscuro,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = icone,
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = descricao,
                color = InovaAzulEscuro,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun GestorStatCard(
    titulo: String,
    valor: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(80.dp)
            .shadow(
                elevation = 3.dp,
                shape = RoundedCornerShape(10.dp),
                ambientColor = InovaPreto.copy(alpha = 0.08f),
                spotColor = InovaPreto.copy(alpha = 0.08f)
            ),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = InovaBranco)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 15.dp, top = 14.dp)
        ) {
            Text(
                text = titulo,
                color = InovaCinzaTexto,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = valor,
                color = InovaAzulEscuro,
                fontSize = 26.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}

@Composable
fun GestorActionButton(
    label: String,
    icon: String,
    backgroundColor: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp)
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(10.dp),
                ambientColor = InovaPreto.copy(alpha = 0.10f),
                spotColor = InovaPreto.copy(alpha = 0.10f)
            )
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(10.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = icon, fontSize = 18.sp)

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = label,
                color = InovaBranco,
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeGestorScreenPreview() {
    ChallengegrupoaguiabrancaTheme {
        HomeGestorScreen()
    }
}
