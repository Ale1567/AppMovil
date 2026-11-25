package com.example.myapplication.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.IncidenciaItem
import com.example.myapplication.repository.ComentarioRepository
import com.example.myapplication.repository.InicioRepository
import kotlinx.coroutines.launch

class IncidenciaViewModel(
    application: Application,
    private val idIncidencia: String
) : AndroidViewModel(application) {

    private val inicioRepository = InicioRepository(application)
    private val comentarioRepository = ComentarioRepository()

    var incidencia by mutableStateOf<IncidenciaItem?>(
        inicioRepository.obtenerIncidenciaPorId(idIncidencia)
    )
        private set

    var nuevoComentario by mutableStateOf("")
        private set

    var cargandoComentarios by mutableStateOf(false)
        private set

    var enviandoComentario by mutableStateOf(false)
        private set

    init {
        cargarComentarios()
    }

    fun onComentarioChange(texto: String) {
        nuevoComentario = texto
    }

    fun cargarComentarios() {
        val actual = incidencia ?: return
        viewModelScope.launch {
            cargandoComentarios = true
            val comentariosRemotos = comentarioRepository.obtenerComentariosPorIncidencia(idIncidencia)
            incidencia = actual.copy(comentarios = comentariosRemotos)
            cargandoComentarios = false
        }
    }

    fun publicarComentario() {
        val actual = incidencia ?: return
        if (!comentarioRepository.esComentarioValido(nuevoComentario) || enviandoComentario) return

        val textoAEnviar = nuevoComentario

        viewModelScope.launch {
            enviandoComentario = true
            val creadoTexto = comentarioRepository.publicarComentario(
                idIncidencia = idIncidencia,
                texto = textoAEnviar
            )
            enviandoComentario = false

            if (creadoTexto != null) {
                incidencia = actual.copy(comentarios = actual.comentarios + creadoTexto)
                nuevoComentario = ""
            }
        }
    }
}