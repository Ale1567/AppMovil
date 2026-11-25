package com.example.myapplication.repository

import com.example.myapplication.model.LoginModel
import com.example.myapplication.model.UsuarioDto
import com.example.myapplication.remote.RetrofitInstance

class LoginRepository {

    fun verificarNombre(formulario: LoginModel): Boolean {
        return formulario.nombre.isBlank()
    }

    fun verificarContraseña(formulario: LoginModel): Boolean {
        return formulario.contrasena.isBlank()
    }

    fun esFormularioValido(formulario: LoginModel): Boolean {
        return !verificarNombre(formulario) && !verificarContraseña(formulario)
    }

    suspend fun iniciarSesion(formulario: LoginModel): UsuarioDto? {
        val datoUsuario = formulario.nombre.trim()
        val clave = formulario.contrasena

        return try {
            val usuarios = RetrofitInstance.usuariosApi.obtenerUsuarios()
            usuarios.find { usuario ->
                val coincideUsuario = usuario.nombre.equals(datoUsuario, ignoreCase = true) ||
                        usuario.email.equals(datoUsuario, ignoreCase = true)
                coincideUsuario && usuario.password == clave
            }
        } catch (_: Exception) {
            null
        }
    }

    fun guardarSesionLocal(manejoPersist: ManejoPersist, usuario: UsuarioDto) {
        manejoPersist.guardarUsuario(
            nombre = usuario.nombre ?: "",
            correo = usuario.email ?: "",
            contrasena = usuario.password ?: ""
        )
        manejoPersist.guardarFoto(usuario.foto_perfil)
        manejoPersist.setLoggedIn(true)
    }
}