package com.example.myapplication.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.myapplication.model.IncidenciaItem
import com.example.myapplication.repository.InicioRepository

class IncidenciaViewModel(private val idIncidencia: String) : ViewModel() {

    private val repository = InicioRepository()

    var incidencia by mutableStateOf<IncidenciaItem?>(
        repository.obtenerIncidencias().find { it.id == idIncidencia }
    )
        private set

    var nuevoComentario by mutableStateOf("")
        private set

    fun onComentarioChange(texto: String) {
        nuevoComentario = texto
    }

    fun publicarComentario(){
        if (nuevoComentario.isNotBlank() && incidencia != null){
            val comentariosActualizados = incidencia!!.comentarios + nuevoComentario
            val listaGlobal = InicioRepository.listaIncidencias
            val index = listaGlobal.indexOfFirst { it.id == idIncidencia }
            if (index != -1){
                listaGlobal[index] = incidencia!!.copy(comentarios = comentariosActualizados)
                incidencia = listaGlobal[index]
            }
            nuevoComentario = ""
        }
    }
}