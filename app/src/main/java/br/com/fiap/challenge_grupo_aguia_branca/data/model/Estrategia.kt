package br.com.fiap.challenge_grupo_aguia_branca.data.model

data class EstrategiaResponse(
    val id: String,
    val titulo: String,
    val descricao: String,
    val categoria: String,
    val campanha: String,
    val inicioVigencia: String,
    val fimVigencia: String
)

data class EstrategiaRequest(
    val titulo: String,
    val descricao: String,
    val categoria: String,
    val campanha: String,
    val inicioVigencia: String,
    val fimVigencia: String
)
