package com.example.myapplication.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.RedactModel
import com.example.myapplication.repository.RedactRepository
import kotlinx.coroutines.launch

class RedactViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = RedactRepository(application)

    var estado by mutableStateOf(repository.getFormulario())
        private set

    var cargando by mutableStateOf(false)
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
        get() = repository.esFormularioValido(estado)

    fun publicarIncidencia(onSuccess: () -> Unit) {
        if (!isFormularioValido || cargando) return

        viewModelScope.launch {
            cargando = true
            val resultado = repository.publicarIncidencia(estado)
            cargando = false

            if (resultado != null) {
                estado = RedactModel()
                onSuccess()
            }
        }
    }
}