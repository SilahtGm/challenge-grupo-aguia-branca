package br.com.fiap.challenge_grupo_aguia_branca.data.model

data class LoginRequest(
    val email: String,
    val senha: String
)

data class LoginResponse(
    val token: String,
    val usuarioId: String,
    val nome: String,
    val email: String,
    val perfis: List<String>
)

data class UsuarioSessao(
    val usuarioId: String,
    val nome: String,
    val email: String,
    val perfis: List<String>
) {
    val perfilPrincipal: String
        get() = perfis.firstOrNull().orEmpty()
}
