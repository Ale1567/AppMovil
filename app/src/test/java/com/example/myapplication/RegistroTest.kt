package com.example.myapplication

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class RegistroTest {

    private fun validarFormatoCorreo(correo: String): String? {
        val limpio = correo.trim()
        val regexCorreo = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
        return if (!regexCorreo.matches(limpio)) {
            "El correo está en mal formato"
        } else {
            null
        }
    }

    @Test
    fun detectaCorreoConMalFormato() {
        val error = validarFormatoCorreo("correo_sin_arroba.cl")

        assertEquals("El correo está en mal formato", error)
        assertFalse(error == null)
    }

    @Test
    fun aceptaCorreoConFormatoValido() {
        val error = validarFormatoCorreo("vecino@correo.cl")

        assertNull(error)
        assertTrue(error == null)
    }
}