package com.example.myapplication.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.myapplication.repository.InicioRepository

class InicioViewModel: ViewModel() {

    private val repository = InicioRepository()

    var estado by mutableStateOf(repository.getInicio())
    private set

    fun cargarIncidencias() {
        estado = repository.getInicio()
    }

    fun onBuscadorChange(nuevaBusqueda: String){
        estado = estado.copy(buscador = nuevaBusqueda)
    }
}