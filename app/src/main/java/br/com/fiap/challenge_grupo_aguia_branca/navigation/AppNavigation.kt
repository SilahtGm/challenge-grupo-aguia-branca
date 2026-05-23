package br.com.fiap.challenge_grupo_aguia_branca.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.fiap.challenge_grupo_aguia_branca.screens.LoginScreen
import br.com.fiap.challenge_grupo_aguia_branca.viewmodel.ApiViewModel

enum class FluxoApp {
    LOGIN,
    OPERADOR,
    GESTOR,
    LIDER
}

@Composable
fun AppNavigation(viewModel: ApiViewModel = viewModel()) {
    var fluxoAtual by rememberSaveable {
        mutableStateOf(FluxoApp.LOGIN)
    }

    when (fluxoAtual) {
        FluxoApp.LOGIN -> {
            LoginScreen(
                viewModel = viewModel,
                onLoginSucesso = { perfil ->
                    fluxoAtual = when (perfil.uppercase()) {
                        "GESTOR" -> FluxoApp.GESTOR
                        "LIDER", "LÍDER", "LIDERANCA", "LIDERANÇA" -> FluxoApp.LIDER
                        else -> FluxoApp.OPERADOR
                    }
                }
            )
        }

        FluxoApp.OPERADOR -> {
            OperadorNavigation(
                viewModel = viewModel,
                onLogout = { fluxoAtual = FluxoApp.LOGIN }
            )
        }

        FluxoApp.GESTOR -> {
            GestorNavigation(
                viewModel = viewModel,
                onLogout = { fluxoAtual = FluxoApp.LOGIN }
            )
        }

        FluxoApp.LIDER -> {
            LiderNavigation(
                viewModel = viewModel,
                onLogout = { fluxoAtual = FluxoApp.LOGIN }
            )
        }
    }
}
