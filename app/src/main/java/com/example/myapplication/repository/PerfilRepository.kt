package com.example.myapplication.repository

import com.example.myapplication.model.PerfilModel

class PerfilRepository {

    companion object {
        var perfilActual = PerfilModel(
            nombre = "Ale",
            correo = "usuario@ejemplo.com",
            descripcion = "vecino sector este",
            fotoPerfil = null,
            valoracion = "5/10"
        )
    }

    fun obtenerPerfil(): PerfilModel {
        return perfilActual
    }

    fun actualizarPerfil(nuevoNombre: String, nuevaDescripcion: String) {
        perfilActual = perfilActual.copy(
            nombre = nuevoNombre,
            descripcion = nuevaDescripcion
        )
    }
}