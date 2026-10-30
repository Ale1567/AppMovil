package com.example.myapplication.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.myapplication.model.IncidenciaItem
import com.example.myapplication.repository.InicioRepository

class IncidenciaViewModel(application: Application, private val idIncidencia: String) : AndroidViewModel(application) {

    private val repository = InicioRepository(application)

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
            val listaGlobal = repository.obtenerIncidencias()
            val index = listaGlobal.indexOfFirst { it.id == idIncidencia }

            if (index != -1){
                listaGlobal[index] = incidencia!!.copy(comentarios = comentariosActualizados)
                incidencia = listaGlobal[index]

                val manejoPersist = com.example.myapplication.repository.ManejoPersist(getApplication())
                manejoPersist.guardarIncidencias(listaGlobal)
            }
            nuevoComentario = ""
        }
    }
}