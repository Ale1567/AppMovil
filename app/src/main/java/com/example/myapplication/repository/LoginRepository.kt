package com.example.myapplication.repository

import com.example.myapplication.model.LoginModel

class LoginRepository {

    private var formulario = LoginModel()

    fun getLogin(): LoginModel = formulario

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

}