package com.example.myapplication.ui.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myapplication.viewmodel.LoginViewModel // Recomendado usar PascalCase

@Composable
fun LoginUi(viewModel: LoginViewModel,
            onNavigateToRegistro:() -> Unit) {
    val formulario = viewModel.formulario

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Ingresa tus datos para iniciar sesión")
        Spacer(modifier = Modifier.height(15.dp))

        OutlinedTextField(
            value = formulario.nombre,
            onValueChange = { viewModel.onNombreChange(it) },
            label = { Text("Ingresa Nombre") },
            isError = viewModel.verificarNombre(),
            supportingText = {
                if (viewModel.verificarNombre()) {
                    Text("El campo esta vacio", color = Color.Red)
                }
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = formulario.contrasena,
            onValueChange = { viewModel.onContrasenaChange(it) },
            label = { Text("Ingresa Contraseña") },
            isError = viewModel.verificarContrasena(),
            supportingText = {
                if (viewModel.verificarContrasena()) {
                    Text("El campo esta vacio", color = Color.Red)
                }
            }
        )

        Spacer(modifier = Modifier.height(15.dp))

        Row(
            modifier = Modifier.fillMaxWidth(0.8f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = { onNavigateToRegistro() }) {
                Text(text = "Registrarse")
            }
            Button(onClick = { /* Acción Recuperar */ }) {
                Text(text = "Recuperar")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { /* Acción Enviar */ },
            enabled = viewModel.isFormularioValido
        ) {
            Text(text = "Iniciar sesion")
        }
    }
}
