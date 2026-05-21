package br.com.fiap.challenge_grupo_aguia_branca.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.challenge_grupo_aguia_branca.data.model.DashboardResumo
import br.com.fiap.challenge_grupo_aguia_branca.data.model.RegistroRequest
import br.com.fiap.challenge_grupo_aguia_branca.data.model.RegistroResponse
import br.com.fiap.challenge_grupo_aguia_branca.data.model.UsuarioResponse
import br.com.fiap.challenge_grupo_aguia_branca.data.remote.RetrofitClient
import br.com.fiap.challenge_grupo_aguia_branca.data.service.DashboardService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ApiViewModel : ViewModel() {

    private val api = RetrofitClient.apiService

    private val _usuarioLogado = MutableStateFlow<UsuarioResponse?>(null)
    val usuarioLogado: StateFlow<UsuarioResponse?> = _usuarioLogado

    private val _registros = MutableStateFlow<List<RegistroResponse>>(emptyList())
    val registros: StateFlow<List<RegistroResponse>> = _registros

    private val _dashboard = MutableStateFlow<DashboardResumo?>(null)
    val dashboard: StateFlow<DashboardResumo?> = _dashboard

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

    fun listarIdeias() {
        listarRegistrosPorTipo("IDEIA")
    }

    fun listarProjetos() {
        listarRegistrosPorTipo("PROJETO")
    }

    fun listarOrientacoes() {
        listarRegistrosPorTipo("ORIENTACAO")
    }

    fun listarMinhasIdeias() {
        viewModelScope.launch {
            try {
                val usuario = _usuarioLogado.value

                if (usuario == null) {
                    _erro.value = "Usuário não autenticado."
                    return@launch
                }

                val ideias = api.listarRegistrosPorTipo("IDEIA")
                _registros.value = ideias.filter { it.operadorId == usuario.id }
                _erro.value = null
            } catch (e: Exception) {
                _erro.value = "Erro ao carregar suas ideias."
            }
        }
    }

    fun listarIdeiasPorOrientacao(orientacaoId: String) {
        viewModelScope.launch {
            try {
                val ideias = api.listarRegistrosPorTipo("IDEIA")
                _registros.value = ideias.filter { it.orientacaoId == orientacaoId }
                _erro.value = null
            } catch (e: Exception) {
                _erro.value = "Erro ao carregar ideias por orientação."
            }
        }
    }

    fun listarProjetosPorOrientacao(orientacaoId: String) {
        viewModelScope.launch {
            try {
                val projetos = api.listarRegistrosPorTipo("PROJETO")
                _registros.value = projetos.filter { it.orientacaoId == orientacaoId }
                _erro.value = null
            } catch (e: Exception) {
                _erro.value = "Erro ao carregar projetos por orientação."
            }
        }
    }

    fun colocarIdeiaEmAnalise(id: String) {
        atualizarRegistroParcial(
            id = id,
            campos = mapOf(
                "status" to "EM_ANALISE"
            ),
            tipo = "IDEIA"
        )
    }

    fun aprovarIdeia(id: String, prioridade: String = "ALTA") {
        atualizarRegistroParcial(
            id = id,
            campos = mapOf(
                "status" to "APROVADA",
                "prioridade" to prioridade
            ),
            tipo = "IDEIA"
        )
    }

    fun reprovarIdeia(id: String) {
        atualizarRegistroParcial(
            id = id,
            campos = mapOf(
                "status" to "REPROVADA"
            ),
            tipo = "IDEIA"
        )
    }

    fun marcarIdeiaComoProjeto(id: String) {
        atualizarRegistroParcial(
            id = id,
            campos = mapOf(
                "status" to "VIROU_PROJETO"
            ),
            tipo = "IDEIA"
        )
    }

    fun atualizarPrioridadeIdeia(id: String, prioridade: String) {
        atualizarRegistroParcial(
            id = id,
            campos = mapOf(
                "prioridade" to prioridade
            ),
            tipo = "IDEIA"
        )
    }

    fun atualizarStatusProjeto(id: String, status: String, etapa: String) {
        atualizarRegistroParcial(
            id = id,
            campos = mapOf(
                "status" to status,
                "etapa" to etapa
            ),
            tipo = "PROJETO"
        )
    }

    fun atualizarResultadosProjeto(
        id: String,
        retornoFinanceiro: Double,
        reducaoCustos: Double,
        ganhoProdutividade: Double
    ) {
        atualizarRegistroParcial(
            id = id,
            campos = mapOf(
                "retornoFinanceiro" to retornoFinanceiro,
                "reducaoCustos" to reducaoCustos,
                "ganhoProdutividade" to ganhoProdutividade
            ),
            tipo = "PROJETO"
        )
    }

    fun atualizarMetaProjeto(
        id: String,
        investimento: Double,
        retornoFinanceiro: Double,
        reducaoCustos: Double,
        ganhoProdutividade: Double,
        prazo: String
    ) {
        atualizarRegistroParcial(
            id = id,
            campos = mapOf(
                "investimento" to investimento,
                "retornoFinanceiro" to retornoFinanceiro,
                "reducaoCustos" to reducaoCustos,
                "ganhoProdutividade" to ganhoProdutividade,
                "prazo" to prazo
            ),
            tipo = "PROJETO"
        )
    }

    fun carregarDashboard() {
        viewModelScope.launch {
            try {
                val projetos = api.listarRegistrosPorTipo("PROJETO")
                _dashboard.value = DashboardService.calcularResumo(projetos)
                _erro.value = null
            } catch (e: Exception) {
                _dashboard.value = null
                _erro.value = "Erro ao carregar dashboard."
            }
        }
    }

    fun ativarRegistro(id: String, tipo: String) {
        atualizarRegistroParcial(
            id = id,
            campos = mapOf(
                "ativo" to true
            ),
            tipo = tipo
        )
    }

    fun desativarRegistro(id: String, tipo: String) {
        atualizarRegistroParcial(
            id = id,
            campos = mapOf(
                "ativo" to false
            ),
            tipo = tipo
        )
    }

    fun logout() {
        _usuarioLogado.value = null
        _registros.value = emptyList()
        _dashboard.value = null
        _erro.value = null
    }

    fun limparErro() {
        _erro.value = null
    }
}