package br.com.fiap.challenge_grupo_aguia_branca.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.challenge_grupo_aguia_branca.data.model.AtualizarAndamentoProjetoRequest
import br.com.fiap.challenge_grupo_aguia_branca.data.model.ConcluirProjetoRequest
import br.com.fiap.challenge_grupo_aguia_branca.data.model.CriarIdeiaRequest
import br.com.fiap.challenge_grupo_aguia_branca.data.model.CriarProjetoRequest
import br.com.fiap.challenge_grupo_aguia_branca.data.model.DashboardResumoResponse
import br.com.fiap.challenge_grupo_aguia_branca.data.model.EstrategiaRequest
import br.com.fiap.challenge_grupo_aguia_branca.data.model.EstrategiaResponse
import br.com.fiap.challenge_grupo_aguia_branca.data.model.IdeiaPontuadaResponse
import br.com.fiap.challenge_grupo_aguia_branca.data.model.IdeiaResponse
import br.com.fiap.challenge_grupo_aguia_branca.data.model.IniciarProjetoRequest
import br.com.fiap.challenge_grupo_aguia_branca.data.model.LoginRequest
import br.com.fiap.challenge_grupo_aguia_branca.data.model.PontuacaoIaResponse
import br.com.fiap.challenge_grupo_aguia_branca.data.model.ProjetoResponse
import br.com.fiap.challenge_grupo_aguia_branca.data.model.RejeitarIdeiaRequest
import br.com.fiap.challenge_grupo_aguia_branca.data.model.UsuarioSessao
import br.com.fiap.challenge_grupo_aguia_branca.data.remote.RetrofitClient
import br.com.fiap.challenge_grupo_aguia_branca.data.remote.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.time.LocalDate

class ApiViewModel : ViewModel() {

    private val api = RetrofitClient.apiService

    private val _usuarioLogado = MutableStateFlow<UsuarioSessao?>(null)
    val usuarioLogado: StateFlow<UsuarioSessao?> = _usuarioLogado

    private val _estrategias = MutableStateFlow<List<EstrategiaResponse>>(emptyList())
    val estrategias: StateFlow<List<EstrategiaResponse>> = _estrategias

    private val _ideias = MutableStateFlow<List<IdeiaResponse>>(emptyList())
    val ideias: StateFlow<List<IdeiaResponse>> = _ideias

    private val _projetos = MutableStateFlow<List<ProjetoResponse>>(emptyList())
    val projetos: StateFlow<List<ProjetoResponse>> = _projetos

    private val _dashboard = MutableStateFlow<DashboardResumoResponse?>(null)
    val dashboard: StateFlow<DashboardResumoResponse?> = _dashboard

    private val _pontuacoesIa = MutableStateFlow<Map<String, PontuacaoIaResponse>>(emptyMap())
    val pontuacoesIa: StateFlow<Map<String, PontuacaoIaResponse>> = _pontuacoesIa

    private val _pontuandoIaIds = MutableStateFlow<Set<String>>(emptySet())
    val pontuandoIaIds: StateFlow<Set<String>> = _pontuandoIaIds

    private val _erro = MutableStateFlow<String?>(null)
    val erro: StateFlow<String?> = _erro

    private val _mensagem = MutableStateFlow<String?>(null)
    val mensagem: StateFlow<String?> = _mensagem

    fun login(email: String, senha: String) {
        viewModelScope.launch {
            try {
                val resposta = api.login(LoginRequest(email, senha))
                SessionManager.token = resposta.token
                _usuarioLogado.value = UsuarioSessao(
                    usuarioId = resposta.usuarioId,
                    nome = resposta.nome,
                    email = resposta.email,
                    perfis = resposta.perfis
                )
                _erro.value = null
            } catch (e: Exception) {
                SessionManager.token = null
                _usuarioLogado.value = null
                _erro.value = if (e is HttpException && e.code() == 401) {
                    "Email ou senha inválidos, por favor, tente novamente."
                } else {
                    "Erro ao realizar login."
                }
            }
        }
    }

    fun listarEstrategias() {
        viewModelScope.launch {
            try {
                _estrategias.value = api.listarEstrategias()
                _erro.value = null
            } catch (e: Exception) {
                _erro.value = mensagemErro(e, "Erro ao carregar diretrizes.")
            }
        }
    }

    fun cadastrarEstrategia(request: EstrategiaRequest, aoConcluir: () -> Unit = {}) {
        viewModelScope.launch {
            try {
                api.criarEstrategia(request)
                _mensagem.value = "Diretriz criada com sucesso."
                _erro.value = null
                listarEstrategias()
                aoConcluir()
            } catch (e: Exception) {
                _erro.value = mensagemErro(e, "Erro ao cadastrar diretriz.")
            }
        }
    }

    fun atualizarEstrategia(id: String, request: EstrategiaRequest, aoConcluir: () -> Unit = {}) {
        viewModelScope.launch {
            try {
                api.atualizarEstrategia(id, request)
                _erro.value = null
                listarEstrategias()
                aoConcluir()
            } catch (e: Exception) {
                _erro.value = mensagemErro(e, "Erro ao atualizar diretriz.")
            }
        }
    }

    fun deletarEstrategia(id: String) {
        viewModelScope.launch {
            try {
                api.deletarEstrategia(id)
                listarEstrategias()
            } catch (e: Exception) {
                _erro.value = mensagemErro(e, "Erro ao remover diretriz.")
            }
        }
    }

    fun listarIdeias() {
        viewModelScope.launch {
            try {
                _ideias.value = api.listarIdeias()
                _erro.value = null
            } catch (e: Exception) {
                _erro.value = mensagemErro(e, "Erro ao carregar ideias.")
            }
        }
    }

    fun listarMinhasIdeias() {
        viewModelScope.launch {
            try {
                val usuario = _usuarioLogado.value
                if (usuario == null) {
                    _erro.value = "Usuário não autenticado."
                    return@launch
                }
                _ideias.value = api.listarIdeias().filter { it.operadorId == usuario.usuarioId }
                _erro.value = null
            } catch (e: Exception) {
                _erro.value = mensagemErro(e, "Erro ao carregar suas ideias.")
            }
        }
    }

    fun cadastrarIdeia(request: CriarIdeiaRequest, aoConcluir: () -> Unit = {}) {
        viewModelScope.launch {
            try {
                api.criarIdeia(request)
                _mensagem.value = "Ideia registrada com sucesso."
                _erro.value = null
                listarMinhasIdeias()
                aoConcluir()
            } catch (e: Exception) {
                _erro.value = mensagemErro(e, "Erro ao cadastrar ideia.")
            }
        }
    }

    fun aprovarIdeia(id: String) {
        viewModelScope.launch {
            try {
                api.aprovarIdeia(id)
                listarIdeias()
            } catch (e: Exception) {
                _erro.value = mensagemErro(e, "Erro ao aprovar ideia.")
            }
        }
    }

    fun pontuarIdeiaComIa(id: String) {
        viewModelScope.launch {
            _pontuandoIaIds.value = _pontuandoIaIds.value + id
            try {
                val pontuacao = api.pontuarIdeiaComIa(id)
                _pontuacoesIa.value = _pontuacoesIa.value + (id to pontuacao)
                _erro.value = null
            } catch (e: Exception) {
                _erro.value = mensagemErro(e, "Erro ao pontuar ideia com IA. Tente novamente em instantes.")
            } finally {
                _pontuandoIaIds.value = _pontuandoIaIds.value - id
            }
        }
    }

    fun rejeitarIdeia(id: String, motivo: String) {
        viewModelScope.launch {
            try {
                api.rejeitarIdeia(id, RejeitarIdeiaRequest(motivo))
                listarIdeias()
            } catch (e: Exception) {
                _erro.value = mensagemErro(e, "Erro ao reprovar ideia.")
            }
        }
    }

    fun listarProjetos() {
        viewModelScope.launch {
            try {
                _projetos.value = api.listarProjetos()
                _erro.value = null
            } catch (e: Exception) {
                _erro.value = mensagemErro(e, "Erro ao carregar projetos.")
            }
        }
    }

    fun cadastrarProjeto(request: CriarProjetoRequest, aoConcluir: () -> Unit = {}) {
        viewModelScope.launch {
            try {
                api.criarProjeto(request)
                _mensagem.value = "Projeto criado com sucesso."
                _erro.value = null
                listarProjetos()
                aoConcluir()
            } catch (e: Exception) {
                _erro.value = mensagemErro(e, "Erro ao cadastrar projeto.")
            }
        }
    }

    fun iniciarProjeto(id: String) {
        viewModelScope.launch {
            try {
                api.iniciarProjeto(id, IniciarProjetoRequest(dataHoje()))
                listarProjetos()
            } catch (e: Exception) {
                _erro.value = mensagemErro(e, "Erro ao iniciar projeto.")
            }
        }
    }

    fun avancarAndamentoProjeto(id: String, etapa: String, percentualConclusao: Int) {
        viewModelScope.launch {
            try {
                api.atualizarAndamentoProjeto(
                    id,
                    AtualizarAndamentoProjetoRequest(etapa, percentualConclusao)
                )
                listarProjetos()
            } catch (e: Exception) {
                _erro.value = mensagemErro(e, "Erro ao atualizar andamento do projeto.")
            }
        }
    }

    fun concluirProjeto(id: String) {
        viewModelScope.launch {
            try {
                api.concluirProjeto(id, ConcluirProjetoRequest(dataHoje()))
                listarProjetos()
            } catch (e: Exception) {
                _erro.value = mensagemErro(e, "Erro ao concluir projeto.")
            }
        }
    }

    fun carregarDashboard() {
        viewModelScope.launch {
            try {
                _dashboard.value = api.obterResumoDashboard()
                _erro.value = null
            } catch (e: Exception) {
                _dashboard.value = null
                _erro.value = mensagemErro(e, "Erro ao carregar dashboard.")
            }
        }
    }

    fun logout() {
        SessionManager.token = null
        _usuarioLogado.value = null
        _estrategias.value = emptyList()
        _ideias.value = emptyList()
        _projetos.value = emptyList()
        _dashboard.value = null
        _pontuacoesIa.value = emptyMap()
        _pontuandoIaIds.value = emptySet()
        _erro.value = null
        _mensagem.value = null
    }

    fun limparErro() {
        _erro.value = null
    }

    fun limparMensagem() {
        _mensagem.value = null
    }

    private fun mensagemErro(e: Exception, generica: String): String {
        return if (e is HttpException) {
            when (e.code()) {
                401 -> "Sessão expirada. Faça login novamente."
                403 -> "Você não tem permissão para essa ação."
                404 -> "Registro não encontrado."
                else -> generica
            }
        } else {
            generica
        }
    }

    private fun dataHoje(): String = LocalDate.now().toString()
}
