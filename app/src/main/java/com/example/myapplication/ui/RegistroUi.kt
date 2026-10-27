package com.example.myapplication.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myapplication.viewmodel.RegistroViewModel

@Composable
fun RegistroUi(viewModel: RegistroViewModel) {
    val formulario = viewModel.formulario
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Ingresa tus datos para registrarte")
        Spacer(modifier = Modifier.height(15.dp))

        OutlinedTextField(
            value = formulario.nombre,
            onValueChange = { viewModel.onNombreChange(it) },
            label = { Text("Ingresa Nombre") },
            isError = viewModel.verificarNombre(),
            supportingText = {
                if (viewModel.verificarNombre()) {
                    Text("El campo está vacío", color = Color.Red)
                }
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = formulario.correo,
            onValueChange = { viewModel.onCorreoChange(it) },
            label = { Text("Ingresa Correo") },
            isError = viewModel.verificarCorreo(),
            supportingText = {
                if (viewModel.verificarCorreo()) {
                    Text("El campo está vacío", color = Color.Red)
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
                    Text("El campo está vacío", color = Color.Red)
                }
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = formulario.verificacion,
            onValueChange = { viewModel.onVerificacionChange(it) },
            label = { Text("Ingresa Contraseña otra vez") },
            isError = viewModel.verificarVerificacion(),
            supportingText = {
                if (viewModel.verificarVerificacion()) {
                    Text("Las contraseñas no coinciden o está vacío", color = Color.Red)
                }
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Aceptas los términos y condiciones: ")
            Checkbox(
                checked = formulario.terminos,
                onCheckedChange = { viewModel.onTerminosChange(it) } // CORREGIDO
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { /* Acción Registrarse */ },
            enabled = viewModel.isFormularioValido,
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text(text = "Registrarse")
        }
    }
}