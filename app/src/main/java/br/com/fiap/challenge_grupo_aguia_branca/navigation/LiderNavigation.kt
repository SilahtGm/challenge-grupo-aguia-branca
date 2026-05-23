package br.com.fiap.challenge_grupo_aguia_branca.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.fiap.challenge_grupo_aguia_branca.screens.PerfilScreen
import br.com.fiap.challenge_grupo_aguia_branca.screens.liderScreens.DiretrizesLiderScreen
import br.com.fiap.challenge_grupo_aguia_branca.screens.liderScreens.HomeLiderScreen
import br.com.fiap.challenge_grupo_aguia_branca.screens.liderScreens.NovaDiretrizScreen
import br.com.fiap.challenge_grupo_aguia_branca.screens.liderScreens.PortfolioScreen
import br.com.fiap.challenge_grupo_aguia_branca.viewmodel.ApiViewModel

enum class LiderDestination {
    HOME,
    PORTFOLIO,
    DIRETRIZES,
    DIRETRIZ_NOVA,
    PERFIL
}

@Composable
fun LiderNavigation(
    viewModel: ApiViewModel = viewModel(),
    onLogout: () -> Unit = {}
) {
    var currentScreen by rememberSaveable {
        mutableStateOf(LiderDestination.HOME)
    }
    var diretrizSelecionadaId by rememberSaveable {
        mutableStateOf<String?>(null)
    }

    val onNavigate: (LiderDestination) -> Unit = { destination ->
        currentScreen = destination
    }

    when (currentScreen) {
        LiderDestination.HOME -> {
            HomeLiderScreen(
                viewModel = viewModel,
                onNavigate = onNavigate,
                onLogout = {
                    viewModel.logout()
                    onLogout()
                }
            )
        }

        LiderDestination.PORTFOLIO -> {
            PortfolioScreen(
                viewModel = viewModel,
                onNavigate = onNavigate,
                onVoltarClick = { currentScreen = LiderDestination.HOME }
            )
        }

        LiderDestination.DIRETRIZES -> {
            DiretrizesLiderScreen(
                viewModel = viewModel,
                onNavigate = onNavigate,
                onVoltarClick = { currentScreen = LiderDestination.HOME },
                onNovaDiretrizClick = {
                    diretrizSelecionadaId = null
                    currentScreen = LiderDestination.DIRETRIZ_NOVA
                },
                onEditarDiretriz = { id ->
                    diretrizSelecionadaId = id
                    currentScreen = LiderDestination.DIRETRIZ_NOVA
                }
            )
        }

        LiderDestination.DIRETRIZ_NOVA -> {
            NovaDiretrizScreen(
                viewModel = viewModel,
                diretrizId = diretrizSelecionadaId,
                onVoltarClick = { currentScreen = LiderDestination.DIRETRIZES },
                onCancelarClick = { currentScreen = LiderDestination.DIRETRIZES },
                onSalvarClick = { currentScreen = LiderDestination.DIRETRIZES }
            )
        }

        LiderDestination.PERFIL -> {
            PerfilScreen(
                viewModel = viewModel,
                items = liderBottomItems(onNavigate),
                selectedKey = "perfil",
                onVoltarClick = { currentScreen = LiderDestination.HOME }
            )
        }
    }
}

@Composable
fun liderBottomItems(onNavigate: (LiderDestination) -> Unit): List<InovaBottomItem> {
    return listOf(
        InovaBottomItem(
            key = "home",
            icon = "📊",
            label = "Dashboard",
            onClick = { onNavigate(LiderDestination.HOME) }
        ),
        InovaBottomItem(
            key = "portfolio",
            icon = "📈",
            label = "Portfólio",
            onClick = { onNavigate(LiderDestination.PORTFOLIO) }
        ),
        InovaBottomItem(
            key = "diretrizes",
            icon = "🎯",
            label = "Diretrizes",
            onClick = { onNavigate(LiderDestination.DIRETRIZES) }
        ),
        InovaBottomItem(
            key = "perfil",
            icon = "👤",
            label = "Perfil",
            onClick = { onNavigate(LiderDestination.PERFIL) }
        )
    )
}

@Composable
fun LiderBottomBar(
    currentScreen: LiderDestination,
    onNavigate: (LiderDestination) -> Unit,
    modifier: Modifier = Modifier
) {
    val selected = when (currentScreen) {
        LiderDestination.HOME -> "home"
        LiderDestination.PORTFOLIO -> "portfolio"
        LiderDestination.DIRETRIZES, LiderDestination.DIRETRIZ_NOVA -> "diretrizes"
        LiderDestination.PERFIL -> "perfil"
    }

    InovaBottomBar(
        items = liderBottomItems(onNavigate),
        selectedKey = selected,
        modifier = modifier
    )
}
