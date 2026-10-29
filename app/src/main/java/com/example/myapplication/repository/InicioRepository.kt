package com.example.myapplication.repository

import com.example.myapplication.model.IncidenciaItem
import com.example.myapplication.model.InicioModel

class InicioRepository {

    companion object{
        val listaIncidencias = mutableListOf(
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
    }
    fun getInicio(): InicioModel = InicioModel(ultimasIncidencias = listaIncidencias)

    fun obtenerIncidencias(): List<IncidenciaItem> = listaIncidencias

    fun obtenerIncidenciaPorId(id: String): IncidenciaItem?{
        return listaIncidencias.find { it.id == id}
    }
}