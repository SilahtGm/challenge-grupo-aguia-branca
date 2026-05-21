package br.com.fiap.challenge_grupo_aguia_branca.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.challenge_grupo_aguia_branca.navigation.OperadorBottomBar
import br.com.fiap.challenge_grupo_aguia_branca.navigation.OperadorDestination
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.ChallengegrupoaguiabrancaTheme

@Composable
fun PerfilScreen(
    onNavigate: (OperadorDestination) -> Unit = {},
    onVoltarClick: () -> Unit = {}
) {
    val backgroundColor = Color(0xFFF5F6F8)
    val topBarColor = Color(0xFF29476F)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp)
                    .background(topBarColor)
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "←",
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.clickable { onVoltarClick() }
                )

                Spacer(modifier = Modifier.width(18.dp))

                Text(
                    text = "Meu Perfil",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 11.dp)
                    .padding(top = 14.dp, bottom = 74.dp)
            ) {
                PerfilCardOperador()
            }
        }

        OperadorBottomBar(
            currentScreen = OperadorDestination.PERFIL,
            onNavigate = onNavigate,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun PerfilCardOperador() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(190.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .shadow(
                    elevation = 2.dp,
                    shape = RoundedCornerShape(8.dp),
                    ambientColor = Color.Black.copy(alpha = 0.08f),
                    spotColor = Color.Black.copy(alpha = 0.08f)
                ),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
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
                            color = Color(0xFF29476F),
                            shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
                        )
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 23.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AvatarOperador()

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "João Silva",
                        color = Color(0xFF111827),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold
                    )

                    Spacer(modifier = Modifier.height(3.dp))

                    Text(
                        text = "joao.silva@aguiabranca.com",
                        color = Color(0xFF6B7280),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(9.dp))

                    Box(
                        modifier = Modifier
                            .height(22.dp)
                            .background(
                                color = Color(0xFF29476F),
                                shape = RoundedCornerShape(20.dp)
                            )
                            .padding(horizontal = 15.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Operador",
                            color = Color.White,
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
fun AvatarOperador() {
    val avatarColor = Color(0xFF5B2A86)

    Canvas(
        modifier = Modifier.size(58.dp)
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        drawCircle(
            color = avatarColor,
            radius = canvasWidth * 0.22f,
            center = Offset(
                x = canvasWidth / 2,
                y = canvasHeight * 0.27f
            )
        )

        drawOval(
            color = avatarColor,
            topLeft = Offset(
                x = canvasWidth * 0.18f,
                y = canvasHeight * 0.44f
            ),
            size = Size(
                width = canvasWidth * 0.64f,
                height = canvasHeight * 0.48f
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PerfilScreenPreview() {
    ChallengegrupoaguiabrancaTheme {
        PerfilScreen()
    }
}