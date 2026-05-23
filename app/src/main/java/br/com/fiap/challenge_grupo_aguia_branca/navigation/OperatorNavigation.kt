package br.com.fiap.challenge_grupo_aguia_branca.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.fiap.challenge_grupo_aguia_branca.screens.PerfilScreen
import br.com.fiap.challenge_grupo_aguia_branca.screens.operatorScreens.GuidelineScreen
import br.com.fiap.challenge_grupo_aguia_branca.screens.operatorScreens.HomeOperadorScreen
import br.com.fiap.challenge_grupo_aguia_branca.screens.operatorScreens.IdeaRegisterScreen
import br.com.fiap.challenge_grupo_aguia_branca.screens.operatorScreens.IdeasScreen
import br.com.fiap.challenge_grupo_aguia_branca.viewmodel.ApiViewModel

enum class OperadorDestination {
    HOME,
    IDEIAS,
    DIRETRIZES,
    PERFIL,
    IDEA_REGISTER
}

@Composable
fun OperadorNavigation(
    viewModel: ApiViewModel = viewModel(),
    onLogout: () -> Unit = {}
) {
    var currentScreen by rememberSaveable {
        mutableStateOf(OperadorDestination.HOME)
    }

    val onNavigate: (OperadorDestination) -> Unit = { destination ->
        currentScreen = destination
    }

    when (currentScreen) {
        OperadorDestination.HOME -> {
            HomeOperadorScreen(
                viewModel = viewModel,
                onNavigate = onNavigate,
                onLogout = {
                    viewModel.logout()
                    onLogout()
                }
            )
        }

        OperadorDestination.IDEIAS -> {
            IdeasScreen(
                viewModel = viewModel,
                onNavigate = onNavigate,
                onVoltarClick = {
                    currentScreen = OperadorDestination.HOME
                }
            )
        }

        OperadorDestination.DIRETRIZES -> {
            GuidelineScreen(
                viewModel = viewModel,
                onNavigate = onNavigate,
                onVoltarClick = {
                    currentScreen = OperadorDestination.HOME
                }
            )
        }

        OperadorDestination.PERFIL -> {
            PerfilScreen(
                viewModel = viewModel,
                items = operadorBottomItems(onNavigate),
                selectedKey = "perfil",
                onVoltarClick = {
                    currentScreen = OperadorDestination.HOME
                }
            )
        }

        OperadorDestination.IDEA_REGISTER -> {
            IdeaRegisterScreen(
                viewModel = viewModel,
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
    }
}

@Composable
fun operadorBottomItems(onNavigate: (OperadorDestination) -> Unit): List<InovaBottomItem> {
    return listOf(
        InovaBottomItem(
            key = "home",
            icon = "🏠",
            label = "Home",
            onClick = { onNavigate(OperadorDestination.HOME) }
        ),
        InovaBottomItem(
            key = "ideias",
            icon = "💡",
            label = "Ideias",
            onClick = { onNavigate(OperadorDestination.IDEIAS) }
        ),
        InovaBottomItem(
            key = "diretrizes",
            icon = "📋",
            label = "Diretrizes",
            onClick = { onNavigate(OperadorDestination.DIRETRIZES) }
        ),
        InovaBottomItem(
            key = "perfil",
            icon = "👤",
            label = "Perfil",
            onClick = { onNavigate(OperadorDestination.PERFIL) }
        )
    )
}

@Composable
fun OperadorBottomBar(
    currentScreen: OperadorDestination,
    onNavigate: (OperadorDestination) -> Unit,
    modifier: Modifier = Modifier
) {
    val selected = when (currentScreen) {
        OperadorDestination.HOME -> "home"
        OperadorDestination.IDEIAS, OperadorDestination.IDEA_REGISTER -> "ideias"
        OperadorDestination.DIRETRIZES -> "diretrizes"
        OperadorDestination.PERFIL -> "perfil"
    }

    InovaBottomBar(
        items = operadorBottomItems(onNavigate),
        selectedKey = selected,
        modifier = modifier
    )
}
