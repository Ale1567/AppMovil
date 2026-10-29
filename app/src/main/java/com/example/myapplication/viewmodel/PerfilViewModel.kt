package com.example.myapplication.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.myapplication.model.PerfilModel
import com.example.myapplication.repository.InicioRepository
import com.example.myapplication.repository.PerfilRepository

class PerfilViewModel : ViewModel() {

    private val perfilRepository = PerfilRepository()
    private val inicioRepository = InicioRepository()

    var estado by mutableStateOf(
        PerfilModel(
            perfilRepository.obtenerPerfil().nombre,
            perfilRepository.obtenerPerfil().correo,
            perfilRepository.obtenerPerfil().descripcion,
            perfilRepository.obtenerPerfil().fotoPerfil,
            perfilRepository.obtenerPerfil().valoracion,
            inicioRepository.obtenerIncidencias()

        )
    )
        private set
}