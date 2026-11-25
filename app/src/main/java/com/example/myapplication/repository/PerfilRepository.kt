package com.example.myapplication.repository

import android.content.Context
import com.example.myapplication.model.PerfilModel
import com.example.myapplication.model.UsuarioDto
import com.example.myapplication.remote.RetrofitInstance

class PerfilRepository(context: Context) {

    private val manejoPersist = ManejoPersist(context)
    private val inicioRepository = InicioRepository(context)

    fun obtenerPerfil(): PerfilModel {
        return PerfilModel(
            nombre = manejoPersist.obtenerNombre(),
            correo = manejoPersist.obtenerCorreo(),
            descripcion = "vecino sector este",
            fotoPerfil = manejoPersist.obtenerFoto(),
            valoracion = "5/10",
            incidencias = inicioRepository.obtenerIncidenciasLocales()
        )
    }

    suspend fun refrescarPerfil(): PerfilModel {
        val local = obtenerPerfil()
        return try {
            val usuarios = RetrofitInstance.usuariosApi.obtenerUsuarios()
            val encontrado = usuarios.find {
                it.nombre.equals(local.nombre, ignoreCase = true) ||
                        it.email.equals(local.correo, ignoreCase = true)
            }

            if (encontrado != null) {
                val nombreActualizado = encontrado.nombre ?: local.nombre
                val correoActualizado = encontrado.email ?: local.correo
                val fotoActualizada = encontrado.foto_perfil?.takeIf { it.isNotBlank() } ?: local.fotoPerfil
                val descActualizada = encontrado.descripcion?.takeIf { it.isNotBlank() } ?: local.descripcion
                val valoracionActualizada = encontrado.valoracion?.let { "$it/10" } ?: local.valoracion

                manejoPersist.guardarUsuario(nombreActualizado, correoActualizado, encontrado.password ?: "")
                manejoPersist.guardarFoto(fotoActualizada)

                PerfilModel(
                    nombre = nombreActualizado,
                    correo = correoActualizado,
                    descripcion = descActualizada,
                    fotoPerfil = fotoActualizada,
                    valoracion = valoracionActualizada,
                    incidencias = inicioRepository.obtenerIncidenciasRemotas()
                )
            } else {
                local
            }
        } catch (_: Exception) {
            local
        }
    }

    suspend fun actualizarPerfil(
        nuevoNombre: String,
        nuevaDescripcion: String,
        nuevoCorreo: String,
        nuevaFoto: String? = null
    ): PerfilModel {
        val actual = obtenerPerfil()
        val fotoFinal = nuevaFoto ?: actual.fotoPerfil

        manejoPersist.guardarUsuario(nuevoNombre, nuevoCorreo, "")
        manejoPersist.guardarFoto(fotoFinal)

        try {
            val usuarios = RetrofitInstance.usuariosApi.obtenerUsuarios()
            val usuarioActual = usuarios.find {
                it.nombre.equals(actual.nombre, ignoreCase = true) ||
                        it.email.equals(actual.correo, ignoreCase = true)
            }
            val idUsuario = usuarioActual?.id
            if (idUsuario != null) {
                val dtoActualizado = UsuarioDto(
                    id = idUsuario,
                    nombre = nuevoNombre.trim(),
                    email = nuevoCorreo.trim(),
                    password = usuarioActual.password,
                    descripcion = nuevaDescripcion.trim(),
                    foto_perfil = fotoFinal ?: "",
                    valoracion = usuarioActual.valoracion ?: 5
                )
                RetrofitInstance.usuariosApi.actualizarUsuario(idUsuario, dtoActualizado)
            }
        } catch (_: Exception) {
        }

        return PerfilModel(
            nombre = nuevoNombre,
            correo = nuevoCorreo,
            descripcion = nuevaDescripcion,
            fotoPerfil = fotoFinal,
            valoracion = actual.valoracion,
            incidencias = inicioRepository.obtenerIncidenciasLocales()
        )
    }
}