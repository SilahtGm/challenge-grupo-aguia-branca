package br.com.fiap.challenge_grupo_aguia_branca.data.model

const val STATUS_PROJETO_PLANEJADO = 1
const val STATUS_PROJETO_EM_ANDAMENTO = 2
const val STATUS_PROJETO_CONCLUIDO = 3
const val STATUS_PROJETO_CANCELADO = 4

data class ProjetoResponse(
    val id: String,
    val titulo: String,
    val descricao: String,
    val estrategiaId: String,
    val investimentoPrevisto: Double,
    val status: Int,
    val etapa: String,
    val percentualConclusao: Int
)

data class CriarProjetoRequest(
    val titulo: String,
    val descricao: String,
    val estrategiaId: String,
    val inicioPrevisto: String,
    val fimPrevisto: String,
    val investimentoPrevisto: Double,
    val ideiaOrigemId: String? = null
)

data class IniciarProjetoRequest(
    val dataInicio: String
)

data class AtualizarAndamentoProjetoRequest(
    val etapa: String,
    val percentualConclusao: Int
)

data class ConcluirProjetoRequest(
    val dataConclusao: String
)

fun statusProjetoLabel(status: Int): String = when (status) {
    STATUS_PROJETO_EM_ANDAMENTO -> "Em Andamento"
    STATUS_PROJETO_CONCLUIDO -> "Concluído"
    STATUS_PROJETO_CANCELADO -> "Cancelado"
    else -> "Planejado"
}
