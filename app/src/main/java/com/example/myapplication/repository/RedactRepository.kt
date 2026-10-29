package com.example.myapplication.repository

import com.example.myapplication.model.IncidenciaItem
import com.example.myapplication.model.RedactModel
import com.example.myapplication.repository.InicioRepository.Companion.listaIncidencias

class RedactRepository {
    private var formulario = RedactModel()
    fun getFormulario(): RedactModel = formulario

    fun agregarIncidencia(nuevaIncidencia: IncidenciaItem) {
        listaIncidencias.add(0, nuevaIncidencia)
    }
}