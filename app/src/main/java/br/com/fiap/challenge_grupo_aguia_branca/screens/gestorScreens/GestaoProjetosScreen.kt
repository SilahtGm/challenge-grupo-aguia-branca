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
import br.com.fiap.challenge_grupo_aguia_branca.navigation.GestorBottomBar
import br.com.fiap.challenge_grupo_aguia_branca.navigation.GestorDestination
import br.com.fiap.challenge_grupo_aguia_branca.navigation.InovaTopBar
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.ChallengegrupoaguiabrancaTheme
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaAzulClaro
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaAzulEscuro
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaBranco
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaCinza
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaCinzaFundo
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaCinzaTexto
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaPreto
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaVerde
import br.com.fiap.challenge_grupo_aguia_branca.viewmodel.ApiViewModel
import kotlin.math.roundToInt

@Composable
fun GestaoProjetosScreen(
    viewModel: ApiViewModel = viewModel(),
    onNavigate: (GestorDestination) -> Unit = {},
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
                titulo = "Gestão de Projetos",
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
                NovoProjetoBotao {
                    onNavigate(GestorDestination.PROJETO_NOVO)
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (projetos.isEmpty()) {
                    Text(
                        text = "Nenhum projeto cadastrado ainda.",
                        color = InovaCinzaTexto,
                        fontSize = 12.sp
                    )
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(projetos) { projeto ->
                            ProjetoCard(
                                projeto = projeto,
                                onAtualizarAndamento = {
                                    projeto.id?.let { id ->
                                        viewModel.atualizarStatusProjeto(
                                            id = id,
                                            status = "EM_ANDAMENTO",
                                            etapa = "Execução"
                                        )
                                    }
                                },
                                onConcluir = {
                                    projeto.id?.let { id ->
                                        viewModel.atualizarStatusProjeto(
                                            id = id,
                                            status = "CONCLUIDO",
                                            etapa = "Concluído"
                                        )
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }

        GestorBottomBar(
            currentScreen = GestorDestination.PROJETOS,
            onNavigate = onNavigate,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun NovoProjetoBotao(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .shadow(
                elevation = 3.dp,
                shape = RoundedCornerShape(10.dp),
                ambientColor = InovaPreto.copy(alpha = 0.10f),
                spotColor = InovaPreto.copy(alpha = 0.10f)
            )
            .background(
                color = InovaAzulClaro,
                shape = RoundedCornerShape(10.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "+", color = InovaBranco, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "Novo Projeto",
                color = InovaBranco,
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}

@Composable
fun ProjetoCard(
    projeto: RegistroResponse,
    onAtualizarAndamento: () -> Unit,
    onConcluir: () -> Unit
) {
    val progresso = calcularProgresso(projeto.status, projeto.etapa)
    val concluido = projeto.status == "CONCLUIDO"

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
                text = "Projeto: ${projeto.nome ?: projeto.titulo ?: "—"}",
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
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium
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

            Text(
                text = "Etapa: ${projeto.etapa ?: "—"}",
                color = InovaCinzaTexto,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium
            )

            projeto.investimento?.takeIf { it > 0 }?.let {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Investimento: R$ ${it.roundToInt()}",
                    color = InovaCinzaTexto,
                    fontSize = 11.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(36.dp)
                        .background(
                            color = if (concluido) InovaCinza else InovaAzulEscuro,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable(enabled = !concluido) { onAtualizarAndamento() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Em Andamento",
                        color = if (concluido) InovaCinzaTexto else InovaBranco,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(36.dp)
                        .background(
                            color = if (concluido) InovaCinza else InovaVerde,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable(enabled = !concluido) { onConcluir() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (concluido) "Concluído" else "Concluir",
                        color = if (concluido) InovaCinzaTexto else InovaBranco,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
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

@Composable
fun ProgressBarLinha(progresso: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(8.dp)
            .background(
                color = InovaCinza,
                shape = RoundedCornerShape(4.dp)
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(progresso / 100f)
                .height(8.dp)
                .background(
                    color = InovaAzulClaro,
                    shape = RoundedCornerShape(4.dp)
                )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GestaoProjetosScreenPreview() {
    ChallengegrupoaguiabrancaTheme {
        GestaoProjetosScreen()
    }
}
