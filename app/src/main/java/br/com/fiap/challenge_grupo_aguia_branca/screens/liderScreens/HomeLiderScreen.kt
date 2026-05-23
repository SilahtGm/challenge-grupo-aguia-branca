package br.com.fiap.challenge_grupo_aguia_branca.screens.liderScreens

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.fiap.challenge_grupo_aguia_branca.navigation.InovaTopBar
import br.com.fiap.challenge_grupo_aguia_branca.navigation.LiderBottomBar
import br.com.fiap.challenge_grupo_aguia_branca.navigation.LiderDestination
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.ChallengegrupoaguiabrancaTheme
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaAzulClaro
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaAzulEscuro
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaBranco
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaCinzaFundo
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaCinzaTexto
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaPreto
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaVerde
import br.com.fiap.challenge_grupo_aguia_branca.viewmodel.ApiViewModel
import kotlin.math.roundToInt

@Composable
fun HomeLiderScreen(
    viewModel: ApiViewModel = viewModel(),
    onNavigate: (LiderDestination) -> Unit = {},
    onLogout: () -> Unit = {}
) {
    val dashboard by viewModel.dashboard.collectAsState()
    val erro by viewModel.erro.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.carregarDashboard()
    }

    val roiTotal = dashboard?.retornoFinanceiroTotal?.let {
        (it / 1000).roundToInt()
    } ?: 0
    val projetosAtivos = dashboard?.totalProjetos ?: 0

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
                DashboardLiderHeader(
                    titulo = "Dashboard Estratégico",
                    descricao = "Visão consolidada de ROI e impacto",
                    icone = "📈"
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    LiderStatCard(
                        titulo = "ROI GERADO",
                        valor = "R$ ${roiTotal}K",
                        modifier = Modifier.weight(1f),
                        destaque = InovaVerde
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    LiderStatCard(
                        titulo = "PROJETOS ATIVOS",
                        valor = projetosAtivos.toString(),
                        modifier = Modifier.weight(1f),
                        destaque = InovaAzulClaro
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                LiderActionButton(
                    icon = "📈",
                    label = "Visão de Portfólio",
                    background = InovaAzulEscuro,
                    onClick = { onNavigate(LiderDestination.PORTFOLIO) }
                )

                Spacer(modifier = Modifier.height(12.dp))

                LiderActionButton(
                    icon = "🎯",
                    label = "Diretrizes Estratégicas",
                    background = InovaAzulClaro,
                    onClick = { onNavigate(LiderDestination.DIRETRIZES) }
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

        LiderBottomBar(
            currentScreen = LiderDestination.HOME,
            onNavigate = onNavigate,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun DashboardLiderHeader(
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
        colors = CardDefaults.cardColors(containerColor = InovaAzulEscuro)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 18.dp, vertical = 18.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = titulo,
                    color = InovaBranco,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(text = icone, fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = descricao,
                color = InovaBranco.copy(alpha = 0.9f),
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun LiderStatCard(
    titulo: String,
    valor: String,
    destaque: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(86.dp)
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
                color = destaque,
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}

@Composable
fun LiderActionButton(
    icon: String,
    label: String,
    background: Color,
    textColor: Color = InovaBranco,
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
                color = background,
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
                color = textColor,
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeLiderScreenPreview() {
    ChallengegrupoaguiabrancaTheme {
        HomeLiderScreen()
    }
}
