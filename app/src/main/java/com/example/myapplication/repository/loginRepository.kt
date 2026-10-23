package com.example.myapplication.repository

import com.example.myapplication.model.loginModel

class loginRepository {

    private var formulario = loginModel()

    fun getLogin(): loginModel = formulario

    fun verificarNombre(): Boolean {
        if(formulario.nombre=="")
            return false
        else
            return true
    }

    fun verificarContrasena(): Boolean {
        if(formulario.contrasena=="")
            return false
        else
            return true
    }

    fun validarTerminos(): Boolean {
        if(formulario.terminos)
            return true
        else
            return false
    }

}