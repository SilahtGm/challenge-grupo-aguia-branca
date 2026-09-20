package br.com.fiap.challenge_grupo_aguia_branca.data.model

data class PontuacaoIaResponse(
    val pontuacao: Int,
    val prioridade: Int,
    val justificativa: String
)

data class IdeiaPontuadaResponse(
    val ideiaId: String,
    val titulo: String,
    val pontuacao: Int,
    val prioridade: Int,
    val justificativa: String
)

fun prioridadeIaLabel(prioridade: Int): String = when (prioridade) {
    3 -> "Alta"
    2 -> "Média"
    1 -> "Baixa"
    else -> "Não definida"
}
