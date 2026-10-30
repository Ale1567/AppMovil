package com.example.myapplication.repository

import android.content.Context
import com.example.myapplication.model.IncidenciaItem
import com.example.myapplication.model.RedactModel

class RedactRepository(context: Context) {
    private val manejoPersist = ManejoPersist(context)
    private var formulario = RedactModel()

    fun getFormulario(): RedactModel = formulario

    fun agregarIncidencia(nuevaIncidencia: IncidenciaItem) {
        val listaActual = manejoPersist.obtenerIncidencias()
        listaActual.add(0, nuevaIncidencia)
        manejoPersist.guardarIncidencias(listaActual)
    }
}