package com.example.myapplication.repository

import android.util.Patterns
import com.example.myapplication.model.LoginModel
import com.example.myapplication.model.RegistroModel
import com.example.myapplication.model.UsuarioDto
import com.example.myapplication.remote.RetrofitInstance

class RegistroRepository {
    fun verificarNombre(formulario: RegistroModel): Boolean =
        formulario.nombre.isBlank()

    fun verificarCorreo(formulario: RegistroModel): Boolean =
        formulario.correo.isBlank()

    fun verificarContrasena(formulario: RegistroModel): Boolean =
        formulario.contrasena.isBlank()

    fun verificarVerificacion(formulario: RegistroModel): Boolean =
        formulario.verificacion.isBlank() || formulario.verificacion != formulario.contrasena

    fun verificarTerminos(formulario: RegistroModel): Boolean =
        !formulario.terminos

    fun esFormularioValido(formulario: RegistroModel): Boolean =
        !verificarNombre(formulario) &&
                !verificarCorreo(formulario) &&
                !verificarContrasena(formulario) &&
                !verificarVerificacion(formulario) &&
                !verificarTerminos(formulario)

    suspend fun registrarUsuario(formulario: RegistroModel): UsuarioDto? {
        return try {
            val nuevoUsuario = UsuarioDto(
                nombre = formulario.nombre.trim(),
                email = formulario.correo.trim(),
                password = formulario.contrasena,
                descripcion = "",
                foto_perfil = "",
                valoracion = 0
            )
            RetrofitInstance.usuariosApi.crearUsuario(nuevoUsuario)
        } catch (e: Exception) {
            null
        }
    }

    fun guardarUsuarioLocal(manejoPersist: ManejoPersist, usuario: UsuarioDto) {
        manejoPersist.guardarUsuario(
            nombre = usuario.nombre ?: "",
            correo = usuario.email ?: "",
            contrasena = usuario.password ?: ""
        )
        manejoPersist.guardarFoto(usuario.foto_perfil)
        manejoPersist.setLoggedIn(true)
    }
}