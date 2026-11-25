package com.example.myapplication.repository

import android.content.Context
import com.example.myapplication.model.EditModel
import com.example.myapplication.model.UsuarioDto
import com.example.myapplication.remote.RetrofitInstance

class EditRepository(context: Context) {

    private val manejoPersist = ManejoPersist(context)

    fun obtenerEstadoInicial(): EditModel {
        return EditModel(
            nombre = manejoPersist.obtenerNombre(),
            descripcion = "vecino sector este",
            correo = manejoPersist.obtenerCorreo(),
            fotoPerfil = manejoPersist.obtenerFoto()
        )
    }

    fun guardarFotoLocal(fotoBase64: String?) {
        manejoPersist.guardarFoto(fotoBase64)
    }

    suspend fun guardarCambios(estadoActual: EditModel): Pair<Boolean, String?> {
        if (estadoActual.nombre.isBlank() || estadoActual.correo.isBlank()) {
            return false to "El nombre o correo no pueden estar vacíos"
        }

        val contrasenaFinal = if (estadoActual.nuevaContraseña.isNotBlank()) {
            estadoActual.nuevaContraseña.trim()
        } else {
            estadoActual.contraseña.trim()
        }

        manejoPersist.guardarUsuario(
            nombre = estadoActual.nombre.trim(),
            correo = estadoActual.correo.trim(),
            contrasena = contrasenaFinal
        )
        manejoPersist.guardarFoto(estadoActual.fotoPerfil)

        try {
            val usuarios = RetrofitInstance.usuariosApi.obtenerUsuarios()
            val usuarioActual = usuarios.find {
                it.nombre.equals(estadoActual.nombre, ignoreCase = true) ||
                        it.email.equals(estadoActual.correo, ignoreCase = true)
            }
            val idUsuario = usuarioActual?.id
            if (idUsuario != null) {
                val dto = UsuarioDto(
                    id = idUsuario,
                    nombre = estadoActual.nombre.trim(),
                    email = estadoActual.correo.trim(),
                    password = contrasenaFinal.ifBlank { usuarioActual.password },
                    descripcion = estadoActual.descripcion.trim(),
                    foto_perfil = estadoActual.fotoPerfil ?: "",
                    valoracion = usuarioActual.valoracion ?: 5
                )
                RetrofitInstance.usuariosApi.actualizarUsuario(idUsuario, dto)
            }
        } catch (_: Exception) {
      }

        return true to null
    }
}