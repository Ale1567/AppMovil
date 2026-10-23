package com.example.myapplication.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import com.example.myapplication.model.loginModel
import com.example.myapplication.repository.loginRepository

class loginViewModel {
    private val repository = loginRepository()

    val formulario: loginModel by mutableStateOf( repository.getLogin())

    fun verificarFormulario(): Boolean{
        return verificarNombre() &&
                verificarContrasena() &&
                validarTerminos()
    }

    fun verificarNombre(): Boolean {
        if (repository.verificarNombre()){
            return true
        } else {
            return false
        }
    }

    fun verificarContrasena(): Boolean {
        if (repository.verificarContrasena()){
            return true
        } else {
            return false
        }
    }

    fun validarTerminos(): Boolean {
        if (repository.validarTerminos()){
            return true
        } else {
            return false
        }
    }

}