package br.com.fiap.challenge_grupo_aguia_branca.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.challenge_grupo_aguia_branca.screens.LoginScreen
import br.com.fiap.challenge_grupo_aguia_branca.screens.PerfilScreen
import br.com.fiap.challenge_grupo_aguia_branca.screens.operatorScreens.GuidelineScreen
import br.com.fiap.challenge_grupo_aguia_branca.screens.operatorScreens.HomeOperadorScreen
import br.com.fiap.challenge_grupo_aguia_branca.screens.operatorScreens.IdeaRegisterScreen
import br.com.fiap.challenge_grupo_aguia_branca.screens.operatorScreens.IdeasScreen

enum class OperadorDestination {
    HOME,
    IDEIAS,
    DIRETRIZES,
    PERFIL,
    IDEA_REGISTER,
    LOGIN
}

@Composable
fun OperadorNavigation() {
    var currentScreen by rememberSaveable {
        mutableStateOf(OperadorDestination.HOME)
    }

    val onNavigate: (OperadorDestination) -> Unit = { destination ->
        currentScreen = destination
    }

    when (currentScreen) {
        OperadorDestination.HOME -> {
            HomeOperadorScreen(
                onNavigate = onNavigate,
                onLogout = {
                    currentScreen = OperadorDestination.LOGIN
                }
            )
        }

        OperadorDestination.IDEIAS -> {
            IdeasScreen(
                onNavigate = onNavigate,
                onVoltarClick = {
                    currentScreen = OperadorDestination.HOME
                }
            )
        }

        OperadorDestination.DIRETRIZES -> {
            GuidelineScreen(
                onNavigate = onNavigate,
                onVoltarClick = {
                    currentScreen = OperadorDestination.HOME
                }
            )
        }

        OperadorDestination.PERFIL -> {
            PerfilScreen(
                onNavigate = onNavigate,
                onVoltarClick = {
                    currentScreen = OperadorDestination.HOME
                }
            )
        }

        OperadorDestination.IDEA_REGISTER -> {
            IdeaRegisterScreen(
                onVoltarClick = {
                    currentScreen = OperadorDestination.HOME
                },
                onCancelarClick = {
                    currentScreen = OperadorDestination.HOME
                },
                onEnviarClick = {
                    currentScreen = OperadorDestination.IDEIAS
                }
            )
        }

        OperadorDestination.LOGIN -> {
            LoginScreen()
        }
    }
}

@Composable
fun OperadorBottomBar(
    currentScreen: OperadorDestination,
    onNavigate: (OperadorDestination) -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedColor = Color(0xFF29476F)
    val inactiveColor = Color(0xFF7B8494)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
            .background(Color.White)
            .border(
                width = 1.dp,
                color = Color(0xFFE5E7EB)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OperadorBottomItem(
            icon = "🏠",
            label = "Home",
            selected = currentScreen == OperadorDestination.HOME,
            selectedColor = selectedColor,
            inactiveColor = inactiveColor,
            modifier = Modifier.weight(1f),
            onClick = { onNavigate(OperadorDestination.HOME) }
        )

        OperadorBottomItem(
            icon = "💡",
            label = "Ideias",
            selected = currentScreen == OperadorDestination.IDEIAS,
            selectedColor = selectedColor,
            inactiveColor = inactiveColor,
            modifier = Modifier.weight(1f),
            onClick = { onNavigate(OperadorDestination.IDEIAS) }
        )

        OperadorBottomItem(
            icon = "📋",
            label = "Diretrizes",
            selected = currentScreen == OperadorDestination.DIRETRIZES,
            selectedColor = selectedColor,
            inactiveColor = inactiveColor,
            modifier = Modifier.weight(1f),
            onClick = { onNavigate(OperadorDestination.DIRETRIZES) }
        )

        OperadorBottomItem(
            icon = "👤",
            label = "Perfil",
            selected = currentScreen == OperadorDestination.PERFIL,
            selectedColor = selectedColor,
            inactiveColor = inactiveColor,
            modifier = Modifier.weight(1f),
            onClick = { onNavigate(OperadorDestination.PERFIL) }
        )
    }
}

@Composable
fun OperadorBottomItem(
    icon: String,
    label: String,
    selected: Boolean,
    selectedColor: Color,
    inactiveColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val currentColor = if (selected) selectedColor else inactiveColor

    Box(
        modifier = modifier
            .fillMaxHeight()
            .clickable { onClick() }
    ) {
        if (selected) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .height(3.dp)
                    .background(selectedColor)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 7.dp, bottom = 5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = icon,
                fontSize = 17.sp
            )

            Spacer(modifier = Modifier.height(1.dp))

            Text(
                text = label,
                color = currentColor,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}