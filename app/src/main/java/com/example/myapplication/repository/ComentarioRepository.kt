package com.example.myapplication.repository

import com.example.myapplication.model.ComentarioDto
import com.example.myapplication.remote.RetrofitInstance

class ComentarioRepository {

    fun esComentarioValido(texto: String): Boolean {
        return texto.isNotBlank()
    }

    suspend fun obtenerComentariosPorIncidencia(idIncidencia: String): List<String> {
        val idNumerico = idIncidencia.toIntOrNull()
        return try {
            val todos = RetrofitInstance.comentariosApi.obtenerComentarios()
            val filtrados = if (idNumerico != null) {
                todos.filter { it.incidencias_id == idNumerico }
            } else {
                todos
            }
            filtrados.mapNotNull { it.texto?.takeIf { txt -> txt.isNotBlank() } }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun publicarComentario(
        idIncidencia: String,
        texto: String,
        userId: Int = 0
    ): String? {
        if (!esComentarioValido(texto)) return null

        val dto = ComentarioDto(
            texto = texto.trim(),
            incidencias_id = idIncidencia.toIntOrNull() ?: 0,
            usuario_id = userId
        )

        return try {
            val creado = RetrofitInstance.comentariosApi.crearComentario(dto)
            creado.texto?.takeIf { it.isNotBlank() } ?: texto.trim()
        } catch (e: Exception) {
            null
        }
    }
}