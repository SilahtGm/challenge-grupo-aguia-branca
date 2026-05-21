package br.com.fiap.challenge_grupo_aguia_branca.screens.operatorScreens

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.challenge_grupo_aguia_branca.navigation.OperadorBottomBar
import br.com.fiap.challenge_grupo_aguia_branca.navigation.OperadorDestination
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.ChallengegrupoaguiabrancaTheme

@Composable
fun IdeasScreen(
    onNavigate: (OperadorDestination) -> Unit = {},
    onVoltarClick: () -> Unit = {}
) {
    var filtroSelecionado by remember { mutableStateOf("Todas") }

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
                    text = "Minhas Ideias",
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

                Spacer(modifier = Modifier.height(19.dp))

                IdeaRegisterCard()
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
    val selectedColor = Color(0xFF29476F)
    val borderColor = Color(0xFFD9DEE8)

    Box(
        modifier = Modifier
            .height(34.dp)
            .background(
                color = if (selected) selectedColor else Color.White,
                shape = RoundedCornerShape(18.dp)
            )
            .border(
                width = 1.dp,
                color = if (selected) selectedColor else borderColor,
                shape = RoundedCornerShape(18.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 17.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (selected) Color.White else Color(0xFF374151),
            fontSize = 13.sp,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

@Composable
fun IdeaRegisterCard(onNavigate: (OperadorDestination) -> Unit = {},
                     onVoltarClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = Color(0xFFE4E7EC),
                    shape = RoundedCornerShape(8.dp)
                )
        )

        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 4.dp),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 0.dp
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 14.dp)
            ) {
                Text(
                    text = "Redução de Custos",
                    color = Color(0xFF1F2937),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                Spacer(modifier = Modifier.height(7.dp))

                Text(
                    text = "Implementar sistema de rastreamento",
                    color = Color(0xFF6B7280),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "✓ Aprovada",
                    color = Color.Black,
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