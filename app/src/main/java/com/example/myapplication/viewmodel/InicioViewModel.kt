package com.example.myapplication.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.IncidenciaItem
import com.example.myapplication.repository.InicioRepository
import kotlinx.coroutines.launch

class InicioViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = InicioRepository(application)

    private var listaIncidencias: List<IncidenciaItem> = repository.obtenerIncidenciasLocales()

    var estado by mutableStateOf(
        repository.construirEstadoInicio(
            buscador = "",
            incidencias = listaIncidencias
        )
    )
        private set

    var cargando by mutableStateOf(false)
        private set

    init {
        cargarIncidencias()
    }

    fun cargarIncidencias() {
        viewModelScope.launch {
            cargando = true
            listaIncidencias = repository.obtenerIncidenciasRemotas()
            estado = repository.construirEstadoInicio(
                buscador = estado.buscador,
                incidencias = listaIncidencias
            )
            cargando = false
        }
    }

    fun onBuscadorChange(nuevaBusqueda: String) {
        estado = repository.construirEstadoInicio(
            buscador = nuevaBusqueda,
            incidencias = listaIncidencias
        )
    }
}