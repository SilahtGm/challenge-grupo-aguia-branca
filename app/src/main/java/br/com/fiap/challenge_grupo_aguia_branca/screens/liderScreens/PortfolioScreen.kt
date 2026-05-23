package br.com.fiap.challenge_grupo_aguia_branca.screens.liderScreens

import androidx.compose.foundation.background
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
import br.com.fiap.challenge_grupo_aguia_branca.navigation.LiderBottomBar
import br.com.fiap.challenge_grupo_aguia_branca.navigation.LiderDestination
import br.com.fiap.challenge_grupo_aguia_branca.screens.gestorScreens.ProgressBarLinha
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.ChallengegrupoaguiabrancaTheme
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaAzulEscuro
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaBranco
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaCinzaFundo
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaCinzaTexto
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaPreto
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaVerde
import br.com.fiap.challenge_grupo_aguia_branca.viewmodel.ApiViewModel
import kotlin.math.roundToInt

@Composable
fun PortfolioScreen(
    viewModel: ApiViewModel = viewModel(),
    onNavigate: (LiderDestination) -> Unit = {},
    onVoltarClick: () -> Unit = {}
) {
    val projetos by viewModel.registros.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.listarProjetos()
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
                titulo = "Portfólio",
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
                if (projetos.isEmpty()) {
                    Text(
                        text = "Nenhum projeto no portfólio.",
                        color = InovaCinzaTexto,
                        fontSize = 12.sp
                    )
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(projetos) { projeto ->
                            PortfolioCard(projeto)
                        }
                    }
                }
            }
        }

        LiderBottomBar(
            currentScreen = LiderDestination.PORTFOLIO,
            onNavigate = onNavigate,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun PortfolioCard(projeto: RegistroResponse) {
    val progresso = calcularProgresso(projeto.status, projeto.etapa)
    val roi = projeto.retornoFinanceiroTotalLocal()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(10.dp),
                ambientColor = InovaPreto.copy(alpha = 0.08f),
                spotColor = InovaPreto.copy(alpha = 0.08f)
            ),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = InovaBranco)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 14.dp)
        ) {
            Text(
                text = projeto.nome ?: projeto.titulo ?: "—",
                color = InovaAzulEscuro,
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Progresso",
                    color = InovaCinzaTexto,
                    fontSize = 11.sp
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "$progresso%",
                    color = InovaAzulEscuro,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            ProgressBarLinha(progresso = progresso)

            Spacer(modifier = Modifier.height(10.dp))

            Row {
                Text(
                    text = "ROI: ",
                    color = InovaCinzaTexto,
                    fontSize = 11.sp
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "R$ ${roi}K",
                    color = InovaVerde,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }
}

private fun RegistroResponse.retornoFinanceiroTotalLocal(): Int {
    val retorno = retornoFinanceiro ?: 0.0
    return (retorno / 1000).roundToInt()
}

private fun calcularProgresso(status: String?, etapa: String?): Int {
    return when {
        status == "CONCLUIDO" -> 100
        status == "EM_ANDAMENTO" && (etapa?.contains("Execu", ignoreCase = true) == true) -> 65
        status == "EM_ANDAMENTO" -> 50
        status == "PLANEJAMENTO" -> 20
        else -> 10
    }
}

@Preview(showBackground = true)
@Composable
fun PortfolioScreenPreview() {
    ChallengegrupoaguiabrancaTheme {
        PortfolioScreen()
    }
}
