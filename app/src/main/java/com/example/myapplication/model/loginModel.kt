package com.example.myapplication.model

data class loginModel(
    var nombre: String = "",
    var contrasena: String = "",
    val terminos: Boolean = false,
    val modal: Boolean = false
)
