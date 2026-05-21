package br.com.fiap.challenge_grupo_aguia_branca.data.remote

import br.com.fiap.challenge_grupo_aguia_branca.data.model.RegistroRequest
import br.com.fiap.challenge_grupo_aguia_branca.data.model.RegistroResponse
import br.com.fiap.challenge_grupo_aguia_branca.data.model.UsuarioResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    //endpoints usuarios

    @GET("usuarios")
    suspend fun login(
        @Query("email") email: String,
        @Query("senha") senha: String
    ): List<UsuarioResponse>

    @GET("usuarios")
    suspend fun listarUsuarios(): List<UsuarioResponse>

    //fim endpoints usuarios

    //endpoints registros

    @GET("registros")
    suspend fun listarRegistros(): List<RegistroResponse>

    @GET("registros")
    suspend fun listarRegistrosPorTipo(
        @Query("tipo") tipo: String
    ): List<RegistroResponse>

    @POST("registros")
    suspend fun cadastrarRegistro(
        @Body registro: RegistroRequest
    ): RegistroResponse

    @PATCH("registros/{id}")
    suspend fun atualizarRegistroParcial(
        @Path("id") id: String,
        @Body campos: Map<String, Any?>
    ): RegistroResponse

    @DELETE("registros/{id}")
    suspend fun deletarRegistro(
        @Path("id") id: String
    )

    //fim endpoints registros

}