package com.example.myapplication.repository

import android.content.Context
import com.example.myapplication.model.IncidenciaDto
import com.example.myapplication.model.IncidenciaItem
import com.example.myapplication.model.InicioModel
import com.example.myapplication.remote.RetrofitInstance

class InicioRepository(context: Context) {

    private val manejoPersist = ManejoPersist(context)

    private fun mapearDtoAItem(dto: IncidenciaDto): IncidenciaItem {
        return IncidenciaItem(
            id = dto.id?.toString() ?: "",
            titulo = dto.titulo ?: "",
            descripcion = dto.descripcion ?: "",
            imagenUri = dto.imagen_url?.takeIf { it.isNotBlank() }
        )
    }

    suspend fun obtenerIncidenciasRemotas(): List<IncidenciaItem> {
        return try {
            val remotas = RetrofitInstance.incidenciasApi.obtenerIncidencias()
                .map { mapearDtoAItem(it) }
            manejoPersist.guardarIncidencias(remotas)
            remotas
        } catch (e: Exception) {
            manejoPersist.obtenerIncidencias()
        }
    }

    fun obtenerIncidenciasLocales(): MutableList<IncidenciaItem> {
        return manejoPersist.obtenerIncidencias()
    }

    fun filtrarIncidencias(
        incidencias: List<IncidenciaItem>,
        buscador: String
    ): List<IncidenciaItem> {
        if (buscador.isBlank()) return incidencias
        val consulta = buscador.trim()
        return incidencias.filter { item ->
            item.titulo.contains(consulta, ignoreCase = true) ||
                    item.descripcion.contains(consulta, ignoreCase = true)
        }
    }

    fun obtenerIncidenciaPorId(id: String): IncidenciaItem? {
        return obtenerIncidenciasLocales().find { it.id == id }
    }

    fun construirEstadoInicio(
        buscador: String = "",
        incidencias: List<IncidenciaItem> = obtenerIncidenciasLocales()
    ): InicioModel {
        return InicioModel(
            buscador = buscador,
            ultimasIncidencias = filtrarIncidencias(incidencias, buscador)
        )
    }
}