package com.example.myapplication.model

data class RegistroModel (
    var nombre: String = "",
    var correo: String = "",
    var contrasena: String = "",
    var verificacion: String = "",
    var terminos: Boolean = false,
    val modal: Boolean = false
)