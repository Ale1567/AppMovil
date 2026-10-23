package com.example.myapplication.ui.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myapplication.viewmodel.loginViewModel
import com.example.myapplication.model.loginModel
import com.example.myapplication.repository.loginRepository

@Composable
fun loginUi(viewModel: loginViewModel) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "Ingresa tus datos para iniciar sesion")
        Spacer(modifier = Modifier.height(15.dp))

        OutlinedTextField(
            value = viewModel.formulario.nombre,
            onValueChange = { viewModel.formulario.nombre = it },
            label = { Text("Ingresa Nombre") },
            isError = viewModel.verificarNombre(),
            supportingText = {
                if (viewModel.verificarNombre()) {
                    Text("Hay un problema", color = Color.Red)
                }
            }
        )

        OutlinedTextField(
            value = viewModel.formulario.contrasena,
            onValueChange = { viewModel.formulario.contrasena = it },
            label = { Text("Ingresa Contraseña") },
            isError = viewModel.verificarContrasena(),
            supportingText = {
                if (viewModel.verificarContrasena()) {
                    Text("Hay un problema", color = Color.Red)
                }
            }
        )

        Row(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { },
            ) {
                Text(text = "Registrarse")
            }

            Button(
                onClick = { },
            ) {
                Text(text = "Recuperar Contraseña")
            }
        }

        Button(
            onClick = { /* Acción al enviar */ },
            enabled = viewModel.validarTerminos() && !viewModel.verificarNombre()
        ) {
            Text(text = "Enviar")
        }
    }
}