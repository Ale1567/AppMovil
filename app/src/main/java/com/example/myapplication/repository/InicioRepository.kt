package com.example.myapplication.repository

import android.content.Context
import com.example.myapplication.model.IncidenciaItem
import com.example.myapplication.model.InicioModel

class InicioRepository(context: Context) {
    private val manejoPersist = ManejoPersist(context)

    fun getInicio(): InicioModel = InicioModel(ultimasIncidencias = obtenerIncidencias())

    fun obtenerIncidencias(): MutableList<IncidenciaItem> {
        return manejoPersist.obtenerIncidencias()
    }

    fun obtenerIncidenciaPorId(id: String): IncidenciaItem? {
        return obtenerIncidencias().find { it.id == id }
    }
}