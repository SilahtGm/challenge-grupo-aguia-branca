package br.com.fiap.challenge_grupo_aguia_branca.screens.liderScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.ChallengegrupoaguiabrancaTheme
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaAzulClaro
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaAzulEscuro
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaBranco
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaCinzaFundo
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaCinzaTexto
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaPreto
import br.com.fiap.challenge_grupo_aguia_branca.viewmodel.ApiViewModel

@Composable
fun DiretrizesLiderScreen(
    viewModel: ApiViewModel = viewModel(),
    onNavigate: (LiderDestination) -> Unit = {},
    onVoltarClick: () -> Unit = {},
    onNovaDiretrizClick: () -> Unit = {},
    onEditarDiretriz: (String) -> Unit = {}
) {
    val diretrizes by viewModel.registros.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.listarOrientacoes()
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
                titulo = "Diretrizes",
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
                NovaDiretrizBotao(onClick = onNovaDiretrizClick)

                Spacer(modifier = Modifier.height(16.dp))

                if (diretrizes.isEmpty()) {
                    Text(
                        text = "Nenhuma diretriz cadastrada.",
                        color = InovaCinzaTexto,
                        fontSize = 12.sp
                    )
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(diretrizes) { diretriz ->
                            DiretrizLiderCard(
                                diretriz = diretriz,
                                onEditar = { diretriz.id?.let(onEditarDiretriz) },
                                onExcluir = {
                                    diretriz.id?.let { viewModel.deletarRegistro(it, "ORIENTACAO") }
                                }
                            )
                        }
                    }
                }
            }
        }

        LiderBottomBar(
            currentScreen = LiderDestination.DIRETRIZES,
            onNavigate = onNavigate,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun NovaDiretrizBotao(onClick: () -> Unit) {
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
                text = "Nova Diretriz",
                color = InovaBranco,
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}

@Composable
fun DiretrizLiderCard(
    diretriz: RegistroResponse,
    onEditar: () -> Unit,
    onExcluir: () -> Unit
) {
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(4.dp)
                    .background(
                        color = InovaAzulEscuro,
                        shape = RoundedCornerShape(topStart = 10.dp, bottomStart = 10.dp)
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 14.dp, vertical = 14.dp)
            ) {
                Text(
                    text = diretriz.nome ?: diretriz.titulo ?: "—",
                    color = InovaAzulEscuro,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = diretriz.descricao ?: "Sem descrição",
                    color = InovaCinzaTexto,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.weight(1f))

                Row {
                    BotaoMini(
                        label = "✎ Editar",
                        background = InovaAzulEscuro,
                        onClick = onEditar
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    BotaoMini(
                        label = "🗑",
                        background = InovaAzulClaro,
                        onClick = onExcluir
                    )
                }
            }
        }
    }
}

@Composable
fun BotaoMini(
    label: String,
    background: androidx.compose.ui.graphics.Color,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .height(30.dp)
            .background(
                color = background,
                shape = RoundedCornerShape(6.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 14.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            color = InovaBranco,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DiretrizesLiderScreenPreview() {
    ChallengegrupoaguiabrancaTheme {
        DiretrizesLiderScreen()
    }
}
