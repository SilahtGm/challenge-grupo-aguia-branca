package br.com.fiap.challenge_grupo_aguia_branca.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.ChallengegrupoaguiabrancaTheme
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaAzulEscuro
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaBranco
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaLilas

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(InovaAzulEscuro),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.offset(y = (-55).dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Águia Branca Colab",
                color = InovaBranco,
                fontSize = 38.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = (-1).sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Inovação corporativa",
                color = InovaBranco,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )

            Spacer(modifier = Modifier.height(62.dp))

            CircularProgressIndicator(
                modifier = Modifier.size(58.dp),
                color = InovaLilas,
                strokeWidth = 5.dp,
                trackColor = InovaAzulEscuro
            )

            Spacer(modifier = Modifier.height(58.dp))

            Text(
                text = "Grupo Águia Branca",
                color = InovaBranco,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    ChallengegrupoaguiabrancaTheme {
        LoadingScreen()
    }
}
