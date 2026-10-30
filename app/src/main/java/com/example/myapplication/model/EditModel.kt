package com.example.myapplication.model

data class EditModel (
    val nombre: String = "",
    val descripcion: String = "",
    val correo: String = "",
    val contraseña: String = "",
    val nuevaContraseña: String = "",
    val mensajeEstado: String? = null,
    val fotoPerfil: String? = null
)