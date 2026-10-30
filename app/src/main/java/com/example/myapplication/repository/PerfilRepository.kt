package com.example.myapplication.repository

import android.content.Context
import com.example.myapplication.model.PerfilModel

class PerfilRepository(context: Context) {
    private val manejoPersist = ManejoPersist(context)

    fun obtenerPerfil(): PerfilModel {
        return PerfilModel(
            nombre = manejoPersist.obtenerNombre(),
            correo = manejoPersist.obtenerCorreo(),
            descripcion = "vecino sector este",
            fotoPerfil = null,
            valoracion = "5/10"
        )
    }

    fun actualizarPerfil(nuevoNombre: String, nuevaDescripcion: String, nuevoCorreo: String) {
        manejoPersist.guardarUsuario(nuevoNombre, nuevoCorreo, "")
    }
}