package br.com.fiap.challenge_grupo_aguia_branca.data.model

const val STATUS_IDEIA_PENDENTE = 1
const val STATUS_IDEIA_APROVADA = 2
const val STATUS_IDEIA_REJEITADA = 3

data class IdeiaResponse(
    val id: String,
    val titulo: String,
    val descricao: String,
    val operadorId: String,
    val estrategiaId: String,
    val status: Int,
    val prioridade: Int
)

data class CriarIdeiaRequest(
    val titulo: String,
    val descricao: String,
    val estrategiaId: String
)

data class RejeitarIdeiaRequest(
    val motivo: String
)

fun statusIdeiaLabel(status: Int): String = when (status) {
    STATUS_IDEIA_APROVADA -> "Aprovada"
    STATUS_IDEIA_REJEITADA -> "Rejeitada"
    else -> "Pendente"
}
