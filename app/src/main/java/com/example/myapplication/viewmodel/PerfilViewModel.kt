package com.example.myapplication.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.repository.PerfilRepository
import kotlinx.coroutines.launch

class PerfilViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = PerfilRepository(application)

    var estado by mutableStateOf(repository.obtenerPerfil())
        private set

    var cargando by mutableStateOf(false)
        private set

    init {
        refrescarPerfil()
    }

    fun refrescarPerfil() {
        viewModelScope.launch {
            cargando = true
            estado = repository.refrescarPerfil()
            cargando = false
        }
    }

}