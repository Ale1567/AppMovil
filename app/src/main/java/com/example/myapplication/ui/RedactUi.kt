package com.example.myapplication.ui

import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import com.example.myapplication.viewmodel.RedactViewModel
import java.io.File

@Composable
fun RedactUi(
    viewModel: RedactViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToPerfil: () -> Unit
) {
    val estado = viewModel.estado
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null)  }
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }

    val takePictureLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && imageUri != null) {
            try {
                // Forma moderna y segura para cargar bitmaps grandes (vertical u horizontal) sin que fallen
                val source = android.graphics.ImageDecoder.createSource(context.contentResolver, imageUri!!)
                val bmp = android.graphics.ImageDecoder.decodeBitmap(source) { decoder, _, _ ->
                    decoder.isMutableRequired = true
                }
                bitmap = bmp
                viewModel.onImageChange(imageUri.toString())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        granted ->
        if (granted) {
            val file = File.createTempFile("incidencia_foto", ".jpg", context.cacheDir)
            file.deleteOnExit()
            val uri = FileProvider.getUriForFile(
                context, context.packageName + ".provider", file
            )
            imageUri = uri
            takePictureLauncher.launch(uri)
        }
    }

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

        Button(
            onClick = {
                cameraPermissionLauncher.launch(android.Manifest.permission.CAMERA)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Subir Foto")
        }

        if (bitmap != null) {
            Spacer(modifier = Modifier.height(8.dp))

            // Rotación segura que detecta si está en vertical y ajusta sin crashear
            val processedBitmap = remember(bitmap) {
                val bmp = bitmap!!
                // Si el ancho es mayor que el alto (horizontal forzado), lo enderezamos a vertical
                if (bmp.width > bmp.height) {
                    val matrix = android.graphics.Matrix().apply { postRotate(90f) }
                    android.graphics.Bitmap.createBitmap(bmp, 0, 0, bmp.width, bmp.height, matrix, true)
                } else {
                    bmp // Si ya está en vertical, lo dejamos tal cual
                }
            }

            Image(
                bitmap = processedBitmap.asImageBitmap(),
                contentDescription = "Foto Incidencia",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(MaterialTheme.shapes.medium),
                contentScale = androidx.compose.ui.layout.ContentScale.Crop
            )
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