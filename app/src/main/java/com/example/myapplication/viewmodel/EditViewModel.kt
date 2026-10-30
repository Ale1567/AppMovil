package com.example.myapplication.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel // Cambiamos a AndroidViewModel para tener acceso a getApplication()
import com.example.myapplication.model.EditModel
import com.example.myapplication.repository.ManejoPersist

class EditViewModel(application: Application) : AndroidViewModel(application) {

    private val manejoPersist = ManejoPersist(application)

    var estado by mutableStateOf(
        EditModel(
            nombre = manejoPersist.obtenerNombre(),
            descripcion = "vecino sector este",
            correo = manejoPersist.obtenerCorreo()
        )
    )
        private set

    fun onNombreChange(texto: String) { estado = estado.copy(nombre = texto) }
    fun onDescripcionChange(texto: String) { estado = estado.copy(descripcion = texto) }
    fun onCorreoChange(texto: String) { estado = estado.copy(correo = texto) }
    fun onContrasenaActualChange(texto: String) { estado = estado.copy(contraseña = texto) }
    fun onNuevaContrasenaChange(texto: String) { estado = estado.copy(nuevaContraseña = texto) }

    fun guardarCambios(): Boolean {
        if (estado.nombre.isBlank() || estado.correo.isBlank()) {
            estado = estado.copy(mensajeEstado = "El nombre o correo no pueden estar vacíos")
            return false
        }

        manejoPersist.guardarUsuario(
            nombre = estado.nombre,
            correo = estado.correo,
            contrasena = estado.contraseña
        )

        return true
    }

    fun onFotoChange(nuevaUri: String?) {
        estado = estado.copy(fotoPerfil = nuevaUri)
        val manejoPersist = ManejoPersist(getApplication())
        manejoPersist.guardarFoto(nuevaUri)
    }
}