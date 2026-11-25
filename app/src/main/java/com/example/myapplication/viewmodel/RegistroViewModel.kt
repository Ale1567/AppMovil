package com.example.myapplication.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.RegistroModel
import com.example.myapplication.repository.ManejoPersist
import com.example.myapplication.repository.RegistroRepository
import kotlinx.coroutines.launch

class RegistroViewModel : ViewModel() {

    private val repository = RegistroRepository()
    var formulario by mutableStateOf(RegistroModel())
        private set
    var cargando by mutableStateOf(false)
        private set
    var errorRegistro by mutableStateOf(false)
        private set
    fun onNombreChange(nuevoNombre: String) {
        formulario = formulario.copy(nombre = nuevoNombre)
        errorRegistro = false
    }
    fun onCorreoChange(nuevoCorreo: String) {
        formulario = formulario.copy(correo = nuevoCorreo)
        errorRegistro = false
    }
    fun onContrasenaChange(nuevaContrasena: String) {
        formulario = formulario.copy(contrasena = nuevaContrasena)
        errorRegistro = false
    }
    fun onVerificacionChange(nuevaVerificacion: String) {
        formulario = formulario.copy(verificacion = nuevaVerificacion)
        errorRegistro = false
    }
    fun onTerminosChange(cambioTerminos: Boolean) {
        formulario = formulario.copy(terminos = cambioTerminos)
    }

    fun verificarNombre(): Boolean = repository.verificarNombre(formulario)

    fun verificarCorreo(): Boolean = repository.verificarCorreo(formulario)

    fun verificarContrasena(): Boolean = repository.verificarContrasena(formulario)

    fun verificarVerificacion(): Boolean = repository.verificarVerificacion(formulario)

    fun verificarTerminos(): Boolean = repository.verificarTerminos(formulario)

    val isFormularioValido: Boolean
        get() = repository.esFormularioValido(formulario)

    fun registrarUsuario(
        manejoPersist: ManejoPersist,
        onRegistroSuccess: () -> Unit
    ) {
        if (!isFormularioValido) return

        viewModelScope.launch {
            cargando = true
            errorRegistro = false

            val usuarioCreado = repository.registrarUsuario(formulario)

            cargando = false

            if (usuarioCreado != null) {
                repository.guardarUsuarioLocal(manejoPersist, usuarioCreado)
                onRegistroSuccess()
            } else {
                errorRegistro = true
            }
        }
    }
}