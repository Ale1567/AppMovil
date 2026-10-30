package com.example.myapplication.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.myapplication.model.PerfilModel
import com.example.myapplication.repository.InicioRepository
import com.example.myapplication.repository.ManejoPersist

class PerfilViewModel(application: Application) : AndroidViewModel(application) {

    private val manejoPersist = ManejoPersist(application)
    private val inicioRepository = InicioRepository(application)

    var estado by mutableStateOf(
        PerfilModel(
            nombre = manejoPersist.obtenerNombre(),
            correo = manejoPersist.obtenerCorreo(),
            descripcion = "vecino sector este",
            fotoPerfil = manejoPersist.obtenerFoto(),
            valoracion = "5/10",
            incidencias = inicioRepository.obtenerIncidencias()
        )
    )
        private set
}