package br.com.fiap.challenge_grupo_aguia_branca.data.remote

import br.com.fiap.challenge_grupo_aguia_branca.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SessionManager {
    var token: String? = null
}

object RetrofitClient {

    // ponytail: aponta para o emulador Android (10.0.2.2 = localhost da máquina host).
    // Para dispositivo físico, troque pelo IP da máquina na rede local; em produção, use a URL pública da API.
    private const val BASE_URL = "http://10.0.2.2:5200/"

    private val authInterceptor = Interceptor { chain ->
        val builder = chain.request().newBuilder()
        SessionManager.token?.let { builder.addHeader("Authorization", "Bearer $it") }
        chain.proceed(builder.build())
    }

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .addInterceptor(loggingInterceptor)
        .build()

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
