package com.example.myapplication.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.repository.EditRepository
import kotlinx.coroutines.launch

class EditViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = EditRepository(application)

    var estado by mutableStateOf(repository.obtenerEstadoInicial())
        private set

    var guardando by mutableStateOf(false)
        private set

    fun onNombreChange(texto: String) {
        estado = estado.copy(nombre = texto, mensajeEstado = null)
    }

    fun onDescripcionChange(texto: String) {
        estado = estado.copy(descripcion = texto, mensajeEstado = null)
    }

    fun onCorreoChange(texto: String) {
        estado = estado.copy(correo = texto, mensajeEstado = null)
    }

    fun onContrasenaActualChange(texto: String) {
        estado = estado.copy(contraseña = texto, mensajeEstado = null)
    }

    fun onNuevaContrasenaChange(texto: String) {
        estado = estado.copy(nuevaContraseña = texto, mensajeEstado = null)
    }

    fun onFotoChange(nuevaFotoBase64: String?) {
        estado = estado.copy(fotoPerfil = nuevaFotoBase64, mensajeEstado = null)
    }

    fun guardarCambios(onExito: () -> Unit) {
        if (guardando) return

        viewModelScope.launch {
            guardando = true
            val (ok, errorMsg) = repository.guardarCambios(estado)
            guardando = false

            if (ok) {
                onExito()
            } else {
                estado = estado.copy(mensajeEstado = errorMsg)
            }
        }
    }
}