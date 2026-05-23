package br.com.fiap.challenge_grupo_aguia_branca.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.fiap.challenge_grupo_aguia_branca.navigation.InovaBottomBar
import br.com.fiap.challenge_grupo_aguia_branca.navigation.InovaBottomItem
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

@Composable
fun PerfilScreen(
    viewModel: ApiViewModel = viewModel(),
    items: List<InovaBottomItem>,
    selectedKey: String,
    onVoltarClick: () -> Unit = {}
) {
    val usuario by viewModel.usuarioLogado.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(InovaCinzaFundo)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            InovaTopBar(
                titulo = "Meu Perfil",
                mostrarVoltar = true,
                onVoltarClick = onVoltarClick
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 16.dp)
                    .padding(top = 18.dp, bottom = 80.dp)
            ) {
                PerfilCardInfo(
                    nome = usuario?.nome ?: "—",
                    email = usuario?.email ?: "—",
                    perfil = usuario?.perfil?.capitalizar() ?: "—"
                )
            }
        }

        InovaBottomBar(
            items = items,
            selectedKey = selectedKey,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

private fun String.capitalizar(): String {
    return lowercase().replaceFirstChar { it.uppercase() }
}

@Composable
fun PerfilCardInfo(
    nome: String,
    email: String,
    perfil: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(210.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .shadow(
                    elevation = 2.dp,
                    shape = RoundedCornerShape(10.dp),
                    ambientColor = InovaPreto.copy(alpha = 0.08f),
                    spotColor = InovaPreto.copy(alpha = 0.08f)
                ),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(
                containerColor = InovaBranco
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .background(
                            color = InovaAzulEscuro,
                            shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)
                        )
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 25.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AvatarUsuario()

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = nome,
                        color = InovaAzulEscuro,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold
                    )

                    Spacer(modifier = Modifier.height(3.dp))

                    Text(
                        text = email,
                        color = InovaCinzaTexto,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Box(
                        modifier = Modifier
                            .height(24.dp)
                            .background(
                                color = InovaAzulClaro,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .padding(horizontal = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = perfil,
                            color = InovaBranco,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AvatarUsuario() {
    Canvas(
        modifier = Modifier.size(64.dp)
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        drawCircle(
            color = InovaLilas,
            radius = canvasWidth * 0.5f,
            center = Offset(x = canvasWidth / 2, y = canvasHeight / 2)
        )

        drawCircle(
            color = InovaAzulEscuro,
            radius = canvasWidth * 0.20f,
            center = Offset(
                x = canvasWidth / 2,
                y = canvasHeight * 0.32f
            )
        )

        drawOval(
            color = InovaAzulEscuro,
            topLeft = Offset(
                x = canvasWidth * 0.20f,
                y = canvasHeight * 0.52f
            ),
            size = Size(
                width = canvasWidth * 0.60f,
                height = canvasHeight * 0.48f
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PerfilScreenPreview() {
    ChallengegrupoaguiabrancaTheme {
        PerfilCardInfo("João Silva", "joao.silva@aguiabranca.com", "Operador")
    }
}
