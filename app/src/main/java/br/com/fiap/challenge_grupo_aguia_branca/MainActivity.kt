package br.com.fiap.challenge_grupo_aguia_branca

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import br.com.fiap.challenge_grupo_aguia_branca.navigation.OperadorNavigation
import br.com.fiap.challenge_grupo_aguia_branca.screens.LoadingScreen
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.ChallengegrupoaguiabrancaTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            ChallengegrupoaguiabrancaTheme {
                var showLoading by remember { mutableStateOf(true) }

                LaunchedEffect(Unit) {
                    delay(2500)
                    showLoading = false
                }

                if (showLoading) {
                    LoadingScreen()
                } else {
                    OperadorNavigation()
                }
            }
        }
    }
}