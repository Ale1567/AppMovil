package com.example.myapplication.remote

import com.example.myapplication.model.UsuarioDto
import com.example.myapplication.model.IncidenciaDto
import com.example.myapplication.model.ComentarioDto
import retrofit2.http.*

interface ApiService {
    @GET("usuario")
    suspend fun obtenerUsuarios(): List<UsuarioDto>

    @GET("usuario/{usuario_id}")
    suspend fun obtenerUsuariosPorId(@Path("usuario_id") id: Int): UsuarioDto

    @POST("usuario")
    suspend fun crearUsuario(@Body usuario: UsuarioDto): UsuarioDto

    @PATCH("usuario/{usuario_id}")
    suspend fun actualizarUsuario(
        @Path("usuario_id") id: Int,
        @Body datos: UsuarioDto
    ): UsuarioDto


    @GET("incidencia")
    suspend fun obtenerIncidencias(): List<IncidenciaDto>

    @GET("incidencia/{incidencia_id}")
    suspend fun obtenerIncidenciasPorId(@Path("incidencia_id") id: Int): IncidenciaDto

    @POST("incidencia")
    suspend fun crearIncidencia(@Body incidencia: IncidenciaDto): IncidenciaDto

    @DELETE("incidencia/{incidencia_id}")
    suspend fun eliminarIncidencia(@Path("incidencia_id")id: Int)


    @GET("comentario")
    suspend fun obtenerComentarios(): List<ComentarioDto>

    @POST("comentario")
    suspend fun crearComentario(@Body comentario: ComentarioDto): ComentarioDto
    }