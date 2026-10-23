package com.example.myapplication.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.myapplication.model.LoginModel

class LoginViewModel : ViewModel() {

    var formulario by mutableStateOf(LoginModel())
        private set

    fun onNombreChange(nuevoNombre: String) {
        formulario = formulario.copy(nombre = nuevoNombre)
    }

    fun onContrasenaChange(nuevaContrasena: String) {
        formulario = formulario.copy(contrasena = nuevaContrasena)
    }

    // Devuelve true si HAY un error
    fun verificarNombre(): Boolean {
        return formulario.nombre.isBlank()
    }

    fun verificarContrasena(): Boolean {
        return formulario.contrasena.isBlank()
    }

    // El formulario es válido solo si ninguno de los campos tiene errores
    val isFormularioValido: Boolean
        get() = !verificarNombre() && !verificarContrasena()
}