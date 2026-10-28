package com.example.myapplication.repository

import com.example.myapplication.model.IncidenciaItem
import com.example.myapplication.model.RedactModel

class RedactRepository {
    private var formulario = RedactModel()

    fun getFormulario(): RedactModel = formulario

    fun guardarIncidencia(redact: RedactModel): IncidenciaItem {
        return IncidenciaItem(
            id = System.currentTimeMillis().toString(),
            titulo = redact.titulo,
            descripcion = redact.descripcion,
            imagenUri = redact.imagenUri
        )
    }
}