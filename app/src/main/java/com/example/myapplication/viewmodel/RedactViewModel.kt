package com.example.myapplication.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.myapplication.model.IncidenciaItem
import com.example.myapplication.repository.RedactRepository

class RedactViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = RedactRepository(application)

    var estado by mutableStateOf(repository.getFormulario())
        private set

    fun onTituloChange(nuevoTitulo: String) {
        estado = estado.copy(titulo = nuevoTitulo)
    }

    fun onAsuntoChange(nuevaDescripcion: String) {
        estado = estado.copy(descripcion = nuevaDescripcion)
    }

    fun onImageChange(nuevaImagen: String?) {
        estado = estado.copy(imagenUri = nuevaImagen)
    }

    val isFormularioValido: Boolean
        get() = estado.titulo.isNotBlank() && estado.descripcion.isNotBlank()

    fun publicarIncidencia() {
        val nueva = IncidenciaItem(
            id = System.currentTimeMillis().toString(),
            titulo = estado.titulo,
            descripcion = estado.descripcion,
            imagenUri = estado.imagenUri
        )
        repository.agregarIncidencia(nueva)
    }
}