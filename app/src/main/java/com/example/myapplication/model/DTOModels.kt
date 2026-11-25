package com.example.myapplication.model

data class UsuarioDto(
    val id: Int = 0,
    val nombre: String,
    val email: String,
    val password: String,
    val descripcion: String? = null,
    val foto_perfil: String? = null,
    val valoracion: Int? = null
)

data class IncidenciaDto(
    val id: Int = 0,
    val titulo: String,
    val descripcion: String,
    val imagen_url: String? = null,
    val usuario_id: Int = 0
)

data class ComentarioDto(
    val id: Int = 0,
    val texto: String,
    val incidencias_id: Int = 0,
    val usuario_id: Int = 0
)
