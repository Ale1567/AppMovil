package com.example.myapplication.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.LoginModel
import com.example.myapplication.repository.LoginRepository
import com.example.myapplication.repository.ManejoPersist
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val repository = LoginRepository()

    var formulario by mutableStateOf(LoginModel())
        private set

    var errorLogin by mutableStateOf(false)
        private set

    var cargando by mutableStateOf(false)
        private set

    fun onNombreChange(nuevoNombre: String) {
        formulario = formulario.copy(nombre = nuevoNombre)
        errorLogin = false
    }

    fun onContrasenaChange(nuevaContrasena: String) {
        formulario = formulario.copy(contrasena = nuevaContrasena)
        errorLogin = false
    }

    // Devuelve true si HAY un error
    fun verificarNombre(): Boolean = repository.verificarNombre(formulario)

    fun verificarContrasena(): Boolean = repository.verificarContraseña(formulario)

    val isFormularioValido: Boolean
        get() = repository.esFormularioValido(formulario)

    fun iniciarSesion(
        manejoPersist: ManejoPersist,
        onLoginSuccess: () -> Unit
    ) {
        if (!isFormularioValido || cargando) return

        viewModelScope.launch {
            cargando = true
            errorLogin = false

            val usuarioEncontrado = repository.iniciarSesion(formulario)

            cargando = false

            if (usuarioEncontrado != null) {
                repository.guardarSesionLocal(manejoPersist, usuarioEncontrado)
                onLoginSuccess()
            } else {
                errorLogin = true
            }
        }
    }
}