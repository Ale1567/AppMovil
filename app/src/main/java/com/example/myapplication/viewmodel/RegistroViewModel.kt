package com.example.myapplication.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.room.util.copy
import com.example.myapplication.model.LoginModel
import com.example.myapplication.model.RegistroModel
import com.example.myapplication.repository.RegistroRepository

class RegistroViewModel : ViewModel() {

    private val repository = RegistroRepository()

    var formulario by mutableStateOf(repository.getRegistro())
        private set

    fun onNombreChange(nuevoNombre: String) {
        formulario = formulario.copy(nombre = nuevoNombre)
    }

    fun onCorreoChange(nuevoCorreo: String) {
        formulario = formulario.copy(correo = nuevoCorreo)
    }

    fun onContrasenaChange(nuevaContrasena: String) {
        formulario = formulario.copy(contrasena = nuevaContrasena)
    }

    fun onVerificacionChange(nuevaVerificacion: String) {
        formulario = formulario.copy(verificacion = nuevaVerificacion)
    }

    fun onTerminosChange(cambioTerminos: Boolean) {
        formulario = formulario.copy(terminos = cambioTerminos)
    }

    fun verificarNombre(): Boolean = repository.getRegistro().let { formulario.nombre.isBlank() }

    fun verificarCorreo(): Boolean = formulario.correo.isBlank()

    fun verificarContrasena(): Boolean = formulario.contrasena.isBlank()

    fun verificarVerificacion(): Boolean = formulario.verificacion.isBlank() || formulario.verificacion != formulario.contrasena

    fun verificarTerminos(): Boolean = !formulario.terminos

    val isFormularioValido: Boolean
        get() = !formulario.nombre.isBlank() &&
                !formulario.correo.isBlank() &&
                !formulario.contrasena.isBlank() &&
                formulario.verificacion == formulario.contrasena &&
                formulario.terminos
}