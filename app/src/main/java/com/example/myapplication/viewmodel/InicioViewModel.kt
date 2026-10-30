package com.example.myapplication.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.myapplication.repository.InicioRepository

class InicioViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = InicioRepository(application)

    var estado by mutableStateOf(repository.getInicio())
        private set

    fun cargarIncidencias() {
        estado = repository.getInicio()
    }

    fun onBuscadorChange(nuevaBusqueda: String){
        estado = estado.copy(buscador = nuevaBusqueda)
    }
}