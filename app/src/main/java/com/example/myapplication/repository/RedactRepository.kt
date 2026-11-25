package com.example.myapplication.repository

import android.content.Context
import com.example.myapplication.model.IncidenciaDto
import com.example.myapplication.model.IncidenciaItem
import com.example.myapplication.model.RedactModel
import com.example.myapplication.remote.RetrofitInstance

class RedactRepository(context: Context) {

    private val manejoPersist = ManejoPersist(context)
    private var formulario = RedactModel()

    fun getFormulario(): RedactModel = formulario

    fun esFormularioValido(estado: RedactModel): Boolean {
        return estado.titulo.isNotBlank() && estado.descripcion.isNotBlank()
    }

    fun agregarIncidenciaLocal(nuevaIncidencia: IncidenciaItem) {
        val listaActual = manejoPersist.obtenerIncidencias()
        listaActual.add(0, nuevaIncidencia)
        manejoPersist.guardarIncidencias(listaActual)
    }

    suspend fun publicarIncidencia(estado: RedactModel): IncidenciaItem? {
        if (!esFormularioValido(estado)) return null

        val dto = IncidenciaDto(
            titulo = estado.titulo.trim(),
            descripcion = estado.descripcion.trim(),
            imagen_url = estado.imagenUri ?: ""
        )

        return try {
            val creada = RetrofitInstance.incidenciasApi.crearIncidencia(dto)
            val item = IncidenciaItem(
                id = creada.id?.toString() ?: System.currentTimeMillis().toString(),
                titulo = creada.titulo ?: estado.titulo.trim(),
                descripcion = creada.descripcion ?: estado.descripcion.trim(),
                imagenUri = creada.imagen_url?.takeIf { it.isNotBlank() } ?: estado.imagenUri
            )
            agregarIncidenciaLocal(item)
            item
        } catch (e: Exception) {
            val itemOffline = IncidenciaItem(
                id = System.currentTimeMillis().toString(),
                titulo = estado.titulo.trim(),
                descripcion = estado.descripcion.trim(),
                imagenUri = estado.imagenUri
            )
            agregarIncidenciaLocal(itemOffline)
            itemOffline
        }
    }
}