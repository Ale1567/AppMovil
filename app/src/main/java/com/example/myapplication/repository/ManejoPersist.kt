package com.example.myapplication.repository

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri // <-- IMPORT CORRECTO DE ANDROID
import com.example.myapplication.model.IncidenciaItem

class ManejoPersist(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("crimewatch_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_LOGGED_IN = "is_logged_in"
        private const val KEY_NOMBRE = "nombre"
        private const val KEY_CORREO = "correo"
        private const val KEY_CONTRASENA = "contrasena"
        private const val KEY_FOTO = "foto_perfil"
    }

    fun guardarIncidencias(lista: List<IncidenciaItem>) {
        val editor = prefs.edit()
        editor.putInt("inc_count", lista.size)
        lista.forEachIndexed { index, item ->
            editor.putString("inc_id_$index", item.id)
            editor.putString("inc_titulo_$index", item.titulo)
            editor.putString("inc_desc_$index", item.descripcion)
            editor.putString("inc_img_$index", item.imagenUri?.toString() ?: "")
        }
        editor.apply()
    }

    fun obtenerIncidencias(): MutableList<IncidenciaItem> {
        val count = prefs.getInt("inc_count", -1)

        // Si es la primera vez que se abre la app, devolvemos las por defecto y las guardamos
        if (count == -1) {
            val listaDefault = mutableListOf(
                IncidenciaItem(
                    id = "1",
                    titulo = "Robo en un lugar",
                    descripcion = "Roban celulares en dicho lugar",
                    imagenUri = null
                ),
                IncidenciaItem(
                    id = "2",
                    titulo = "Incendio",
                    descripcion = "Se incendia edificio en un lugar",
                    imagenUri = null
                )
            )
            guardarIncidencias(listaDefault)
            return listaDefault
        }

        val lista = mutableListOf<IncidenciaItem>()
        for (i in 0 until count) {
            val id = prefs.getString("inc_id_$i", "") ?: ""
            val titulo = prefs.getString("inc_titulo_$i", "") ?: ""
            val desc = prefs.getString("inc_desc_$i", "") ?: ""
            val imgStr = prefs.getString("inc_img_$i", "")

            // Como imagenUri es String?, pasamos imgStr directamente (o null si está vacío)
            val imgUri = if (!imgStr.isNullOrEmpty()) imgStr else null

            lista.add(IncidenciaItem(id = id, titulo = titulo, descripcion = desc, imagenUri = imgUri))
        }
        return lista
    }

    fun guardarUsuario(nombre: String, correo: String, contrasena: String) {
        prefs.edit().apply {
            putString(KEY_NOMBRE, nombre)
            putString(KEY_CORREO, correo)
            putString(KEY_CONTRASENA, contrasena)
            putBoolean(KEY_LOGGED_IN, true)
            apply()
        }
    }

    fun validarLogin(nombre: String, contrasena: String): Boolean {
        val guardadoNombre = prefs.getString(KEY_NOMBRE, "")
        val guardadoContrasena = prefs.getString(KEY_CONTRASENA, "")
        if (guardadoNombre.isNullOrEmpty()) return false
        return nombre == guardadoNombre && contrasena == guardadoContrasena
    }

    fun setLoggedIn(isLoggedIn: Boolean) {
        prefs.edit().putBoolean(KEY_LOGGED_IN, isLoggedIn).apply()
    }

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean(KEY_LOGGED_IN, false)
    }

    fun obtenerNombre(): String = prefs.getString(KEY_NOMBRE, "Usuario CrimeWatch") ?: "Usuario CrimeWatch"
    fun obtenerCorreo(): String = prefs.getString(KEY_CORREO, "correo@ejemplo.com") ?: "correo@ejemplo.com"

    fun guardarFoto(fotoUri: String?) {
        prefs.edit().putString(KEY_FOTO, fotoUri ?: "").apply()
    }

    fun obtenerFoto(): String? {
        val foto = prefs.getString(KEY_FOTO, "")
        return if (foto.isNullOrEmpty()) null else foto
    }

}