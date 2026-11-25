package com.example.myapplication

import com.example.myapplication.model.LoginModel
import com.example.myapplication.repository.LoginRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class LoginTest {
    private val loginRepository = LoginRepository()

    @Test
    fun formularioInvalidoCuandoCamposEstanVacios() {
        val formularioVacio = LoginModel(nombre = "", contrasena = "")

        assertTrue(loginRepository.verificarNombre(formularioVacio))
        assertTrue(loginRepository.verificarContraseña(formularioVacio))
        assertFalse(loginRepository.esFormularioValido(formularioVacio))
    }

    @Test
    fun formularioValidoCuandoNombreYContrasenaTienenDatos() {
        val formularioValido = LoginModel(nombre = "vecino1", contrasena = "1234")

        assertFalse(loginRepository.verificarNombre(formularioValido))
        assertFalse(loginRepository.verificarContraseña(formularioValido))
        assertTrue(loginRepository.esFormularioValido(formularioValido))
    }
}