package com.example.myapplication.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val BASE_USUARIOS = "https://x8ki-letl-twmt.n7.xano.io/api:v8rb1UDK/"
    private const val BASE_INCIDENCIAS = "https://x8ki-letl-twmt.n7.xano.io/api:incidencias/"
    private const val BASE_COMENTARIOS = "https://x8ki-letl-twmt.n7.xano.io/api:comentarios/"

    private fun buildService(baseUrl: String): ApiService {
        val normalizedUrl = if (baseUrl.endsWith("/")) baseUrl else "$baseUrl/"
        return Retrofit.Builder()
            .baseUrl(normalizedUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    val usuariosApi: ApiService by lazy { buildService(BASE_USUARIOS) }
    val incidenciasApi: ApiService by lazy { buildService(BASE_INCIDENCIAS) }
    val comentariosApi: ApiService by lazy { buildService(BASE_COMENTARIOS) }
}