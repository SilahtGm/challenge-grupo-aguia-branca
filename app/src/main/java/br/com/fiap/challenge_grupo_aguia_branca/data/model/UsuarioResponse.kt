package br.com.fiap.challenge_grupo_aguia_branca.data.model

data class UsuarioResponse(
    val id: String,
    val nome: String,
    val email: String,
    val senha: String,
    val perfil: String
)