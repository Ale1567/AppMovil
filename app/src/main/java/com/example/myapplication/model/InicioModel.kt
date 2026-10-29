package com.example.myapplication.model


data class InicioModel(
    val buscador: String = "",
    val ultimasIncidencias: List<IncidenciaItem> = emptyList(),
    val modal: Boolean = false
)
data class IncidenciaItem(
    val id: String = "",
    val titulo: String = "",
    val descripcion: String = "",
    val imagenUri: String? = null,
    val comentarios: List<String> = emptyList()
)

