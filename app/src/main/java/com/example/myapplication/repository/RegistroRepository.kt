package com.example.myapplication.repository

import com.example.myapplication.model.LoginModel
import com.example.myapplication.model.RegistroModel

class RegistroRepository {
    private var formulario = RegistroModel()

    fun getRegistro(): RegistroModel = formulario

    fun verificarNombre(): Boolean =
        formulario.nombre.isBlank()

    fun verificarCorreo(): Boolean =
        formulario.correo.isBlank()

    fun verificarContrasena(): Boolean =
        formulario.contrasena.isBlank()

    fun verificarVerificacion(): Boolean =
        formulario.verificacion.isBlank() || formulario.verificacion != formulario.contrasena

    fun verificarTerminos(): Boolean =
        !formulario.terminos
}