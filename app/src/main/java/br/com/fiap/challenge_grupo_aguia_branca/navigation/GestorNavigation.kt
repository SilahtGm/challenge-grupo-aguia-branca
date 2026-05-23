package br.com.fiap.challenge_grupo_aguia_branca.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.fiap.challenge_grupo_aguia_branca.screens.PerfilScreen
import br.com.fiap.challenge_grupo_aguia_branca.screens.gestorScreens.CuradoriaIdeiasScreen
import br.com.fiap.challenge_grupo_aguia_branca.screens.gestorScreens.GestaoProjetosScreen
import br.com.fiap.challenge_grupo_aguia_branca.screens.gestorScreens.HomeGestorScreen
import br.com.fiap.challenge_grupo_aguia_branca.screens.gestorScreens.NovoProjetoScreen
import br.com.fiap.challenge_grupo_aguia_branca.viewmodel.ApiViewModel

enum class GestorDestination {
    HOME,
    IDEIAS,
    PROJETOS,
    PROJETO_NOVO,
    PERFIL
}

@Composable
fun GestorNavigation(
    viewModel: ApiViewModel = viewModel(),
    onLogout: () -> Unit = {}
) {
    var currentScreen by rememberSaveable {
        mutableStateOf(GestorDestination.HOME)
    }

    val onNavigate: (GestorDestination) -> Unit = { destination ->
        currentScreen = destination
    }

    when (currentScreen) {
        GestorDestination.HOME -> {
            HomeGestorScreen(
                viewModel = viewModel,
                onNavigate = onNavigate,
                onLogout = {
                    viewModel.logout()
                    onLogout()
                }
            )
        }

        GestorDestination.IDEIAS -> {
            CuradoriaIdeiasScreen(
                viewModel = viewModel,
                onNavigate = onNavigate,
                onVoltarClick = { currentScreen = GestorDestination.HOME }
            )
        }

        GestorDestination.PROJETOS -> {
            GestaoProjetosScreen(
                viewModel = viewModel,
                onNavigate = onNavigate,
                onVoltarClick = { currentScreen = GestorDestination.HOME }
            )
        }

        GestorDestination.PROJETO_NOVO -> {
            NovoProjetoScreen(
                viewModel = viewModel,
                onVoltarClick = { currentScreen = GestorDestination.PROJETOS },
                onCancelarClick = { currentScreen = GestorDestination.PROJETOS },
                onCriarClick = { currentScreen = GestorDestination.PROJETOS }
            )
        }

        GestorDestination.PERFIL -> {
            PerfilScreen(
                viewModel = viewModel,
                items = gestorBottomItems(onNavigate),
                selectedKey = "perfil",
                onVoltarClick = { currentScreen = GestorDestination.HOME }
            )
        }
    }
}

@Composable
fun gestorBottomItems(onNavigate: (GestorDestination) -> Unit): List<InovaBottomItem> {
    return listOf(
        InovaBottomItem(
            key = "home",
            icon = "🏠",
            label = "Home",
            onClick = { onNavigate(GestorDestination.HOME) }
        ),
        InovaBottomItem(
            key = "ideias",
            icon = "💡",
            label = "Ideias",
            onClick = { onNavigate(GestorDestination.IDEIAS) }
        ),
        InovaBottomItem(
            key = "projetos",
            icon = "📁",
            label = "Projetos",
            onClick = { onNavigate(GestorDestination.PROJETOS) }
        ),
        InovaBottomItem(
            key = "perfil",
            icon = "👤",
            label = "Perfil",
            onClick = { onNavigate(GestorDestination.PERFIL) }
        )
    )
}

@Composable
fun GestorBottomBar(
    currentScreen: GestorDestination,
    onNavigate: (GestorDestination) -> Unit,
    modifier: Modifier = Modifier
) {
    val selected = when (currentScreen) {
        GestorDestination.HOME -> "home"
        GestorDestination.IDEIAS -> "ideias"
        GestorDestination.PROJETOS, GestorDestination.PROJETO_NOVO -> "projetos"
        GestorDestination.PERFIL -> "perfil"
    }

    InovaBottomBar(
        items = gestorBottomItems(onNavigate),
        selectedKey = selected,
        modifier = modifier
    )
}
