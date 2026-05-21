package br.com.fiap.challenge_grupo_aguia_branca.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.challenge_grupo_aguia_branca.data.model.RegistroRequest
import br.com.fiap.challenge_grupo_aguia_branca.data.model.RegistroResponse
import br.com.fiap.challenge_grupo_aguia_branca.data.model.UsuarioResponse
import br.com.fiap.challenge_grupo_aguia_branca.data.remote.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ApiViewModel : ViewModel() {

    private val api = RetrofitClient.apiService

    private val _usuarioLogado = MutableStateFlow<UsuarioResponse?>(null)
    val usuarioLogado: StateFlow<UsuarioResponse?> = _usuarioLogado

    private val _registros = MutableStateFlow<List<RegistroResponse>>(emptyList())
    val registros: StateFlow<List<RegistroResponse>> = _registros

    private val _erro = MutableStateFlow<String?>(null)
    val erro: StateFlow<String?> = _erro

    fun login(email: String, senha: String) {
        viewModelScope.launch {
            try {
                val usuarios = api.login(email, senha)
                val usuario = usuarios.firstOrNull()

                if (usuario == null) {
                    _usuarioLogado.value = null
                    _erro.value = "Email ou senha inválidos, por favor, tente novamente."
                    return@launch
                }

                _usuarioLogado.value = usuario
                _erro.value = null
            } catch (e: Exception) {
                _usuarioLogado.value = null
                _erro.value = "Erro ao realizar login."
            }
        }
    }

    fun listarRegistrosPorTipo(tipo: String) {
        viewModelScope.launch {
            try {
                _registros.value = api.listarRegistrosPorTipo(tipo)
                _erro.value = null
            } catch (e: Exception) {
                _erro.value = "Erro ao carregar registros."
            }
        }
    }

    fun cadastrarRegistro(registro: RegistroRequest) {
        viewModelScope.launch {
            try {
                api.cadastrarRegistro(registro)
                listarRegistrosPorTipo(registro.tipo)
                _erro.value = null
            } catch (e: Exception) {
                _erro.value = "Erro ao cadastrar registro."
            }
        }
    }

    fun atualizarRegistroParcial(id: String, campos: Map<String, Any?>, tipo: String) {
        viewModelScope.launch {
            try {
                api.atualizarRegistroParcial(id, campos)
                listarRegistrosPorTipo(tipo)
                _erro.value = null
            } catch (e: Exception) {
                _erro.value = "Erro ao atualizar registro."
            }
        }
    }

    fun deletarRegistro(id: String, tipo: String) {
        viewModelScope.launch {
            try {
                api.deletarRegistro(id)
                listarRegistrosPorTipo(tipo)
                _erro.value = null
            } catch (e: Exception) {
                _erro.value = "Erro ao deletar registro."
            }
        }
    }
}