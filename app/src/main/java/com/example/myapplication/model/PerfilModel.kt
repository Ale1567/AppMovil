package com.example.myapplication.model

data class PerfilModel(
    var nombre: String = "",
    var correo: String = "",
    var descripcion: String = "",
    var fotoPerfil: String? = null,
    val valoracion: String = "5/10",
    val incidencias: List<IncidenciaItem> = emptyList()
)
