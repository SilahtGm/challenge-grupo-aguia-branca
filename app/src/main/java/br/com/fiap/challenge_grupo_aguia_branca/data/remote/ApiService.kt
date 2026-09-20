package br.com.fiap.challenge_grupo_aguia_branca.data.remote

import br.com.fiap.challenge_grupo_aguia_branca.data.model.AtualizarAndamentoProjetoRequest
import br.com.fiap.challenge_grupo_aguia_branca.data.model.ConcluirProjetoRequest
import br.com.fiap.challenge_grupo_aguia_branca.data.model.CriarIdeiaRequest
import br.com.fiap.challenge_grupo_aguia_branca.data.model.CriarProjetoRequest
import br.com.fiap.challenge_grupo_aguia_branca.data.model.DashboardResumoResponse
import br.com.fiap.challenge_grupo_aguia_branca.data.model.EstrategiaRequest
import br.com.fiap.challenge_grupo_aguia_branca.data.model.EstrategiaResponse
import br.com.fiap.challenge_grupo_aguia_branca.data.model.IdeiaResponse
import br.com.fiap.challenge_grupo_aguia_branca.data.model.IdeiaPontuadaResponse
import br.com.fiap.challenge_grupo_aguia_branca.data.model.IniciarProjetoRequest
import br.com.fiap.challenge_grupo_aguia_branca.data.model.LoginRequest
import br.com.fiap.challenge_grupo_aguia_branca.data.model.LoginResponse
import br.com.fiap.challenge_grupo_aguia_branca.data.model.PontuacaoIaResponse
import br.com.fiap.challenge_grupo_aguia_branca.data.model.ProjetoResponse
import br.com.fiap.challenge_grupo_aguia_branca.data.model.RejeitarIdeiaRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @GET("api/estrategias")
    suspend fun listarEstrategias(): List<EstrategiaResponse>

    @POST("api/estrategias")
    suspend fun criarEstrategia(@Body request: EstrategiaRequest): EstrategiaResponse

    @PUT("api/estrategias/{id}")
    suspend fun atualizarEstrategia(
        @Path("id") id: String,
        @Body request: EstrategiaRequest
    ): EstrategiaResponse

    @DELETE("api/estrategias/{id}")
    suspend fun deletarEstrategia(@Path("id") id: String)

    @GET("api/ideias")
    suspend fun listarIdeias(): List<IdeiaResponse>

    @POST("api/ideias")
    suspend fun criarIdeia(@Body request: CriarIdeiaRequest): IdeiaResponse

    @POST("api/ideias/{id}/aprovacao")
    suspend fun aprovarIdeia(@Path("id") id: String): IdeiaResponse

    @POST("api/ideias/{id}/rejeicao")
    suspend fun rejeitarIdeia(
        @Path("id") id: String,
        @Body request: RejeitarIdeiaRequest
    ): IdeiaResponse

    @GET("api/ideias/{id}/pontuacao-ia")
    suspend fun pontuarIdeiaComIa(@Path("id") id: String): PontuacaoIaResponse

    @GET("api/ideias/pontuacao-ia/ranking")
    suspend fun rankearIdeiasComIa(): List<IdeiaPontuadaResponse>

    @GET("api/projetos")
    suspend fun listarProjetos(): List<ProjetoResponse>

    @POST("api/projetos")
    suspend fun criarProjeto(@Body request: CriarProjetoRequest): ProjetoResponse

    @POST("api/projetos/{id}/inicio")
    suspend fun iniciarProjeto(
        @Path("id") id: String,
        @Body request: IniciarProjetoRequest
    ): ProjetoResponse

    @PATCH("api/projetos/{id}/andamento")
    suspend fun atualizarAndamentoProjeto(
        @Path("id") id: String,
        @Body request: AtualizarAndamentoProjetoRequest
    ): ProjetoResponse

    @POST("api/projetos/{id}/conclusao")
    suspend fun concluirProjeto(
        @Path("id") id: String,
        @Body request: ConcluirProjetoRequest
    ): ProjetoResponse

    @GET("api/dashboard/resumo")
    suspend fun obterResumoDashboard(): DashboardResumoResponse
}
