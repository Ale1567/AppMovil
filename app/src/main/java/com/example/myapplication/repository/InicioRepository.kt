package com.example.myapplication.repository

import com.example.myapplication.model.IncidenciaItem
import com.example.myapplication.model.InicioModel

class InicioRepository {
    private val listaInicial = listOf(
        IncidenciaItem(
            id = "1",
            titulo = "Robo en un lugar",
            descripcion = "Roban celulares en dicho lugar",
            imagenUri = null
        ),
        IncidenciaItem(
            id = "2",
            titulo = "Incendio",
            descripcion = "Se incendia edificio en un lugar",
            imagenUri = null
        )
    )
    private var estadoInicio = InicioModel(ultimasIncidencias = listaInicial)

    fun getInicio(): InicioModel = estadoInicio

    fun obtenerIncidencias(): List<IncidenciaItem>{
        return estadoInicio.ultimasIncidencias
    }
}