package com.example.myapplication.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.myapplication.viewmodel.RedactViewModel

@Composable
fun RedactUi(
    viewModel: RedactViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToPerfil: () -> Unit
) {
    val estado = viewModel.estado

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "CrimeWatch", style = MaterialTheme.typography.titleLarge)
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .clickable { onNavigateToPerfil() },
                contentAlignment = Alignment.Center
            ) {
                Text(text = "👤", style = MaterialTheme.typography.bodyLarge)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = estado.titulo,
            onValueChange = { viewModel.onTituloChange(it) },
            label = { Text("Título de la incidencia") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = estado.descripcion,
            onValueChange = { viewModel.onAsuntoChange(it) },
            label = { Text("Descripción") },
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = {
                viewModel.onImagenChange("ruta_ejemplo_foto.jpg")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Subir Foto")
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                viewModel.publicarIncidencia()
                onNavigateBack()
            },
            enabled = viewModel.isFormularioValido,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Publicar")
        }
    }
}