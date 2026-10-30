package com.example.myapplication.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.viewmodel.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LoginUi(
    viewModel: LoginViewModel,
    onNavigateToRegistro: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    val formulario = viewModel.formulario
    var cargando by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "👁️",
            fontSize = 60.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "CrimeWatch",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Ingresa tus datos para iniciar sesión",
            color = MaterialTheme.colorScheme.onBackground
        )
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
            },
            modifier = Modifier.fillMaxWidth()
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
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(15.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { onNavigateToRegistro() },
                enabled = !cargando
            ) {
                Text(text = "Registrarse")
            }
            Button(
                onClick = { /* Acción Recuperar */ },
                enabled = !cargando
            ) {
                Text(text = "Recuperar")
            }
        }

        if (viewModel.errorLogin) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Usuario no encontrado o contraseña incorrecta",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                cargando = true
                coroutineScope.launch(Dispatchers.Main) {
                    delay(1500)
                    cargando = false
                    onLoginSuccess()
                }
            },
            enabled = viewModel.isFormularioValido && !cargando,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (cargando) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp,
                    color = Color.White
                )
            } else {
                Text(text = "Iniciar sesion")
            }
        }
    }
}