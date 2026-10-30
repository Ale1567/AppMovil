package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.repository.ManejoPersist
import com.example.myapplication.ui.*
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.theme.LoginUi
import com.example.myapplication.viewmodel.*

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val sessionManager = ManejoPersist(this)
        val usuarioLogueado = sessionManager.isLoggedIn()

        setContent {
            var esModoOscuro by remember { mutableStateOf(false) }

            MyApplicationTheme(darkTheme = esModoOscuro) {
                val navController = rememberNavController()

                val pantallaInicial = if (usuarioLogueado) "inicio" else "login"

                NavHost(navController = navController, startDestination = pantallaInicial) {

                    composable("login") {
                        val loginViewModel: LoginViewModel = viewModel()

                        LoginUi(
                            viewModel = loginViewModel,
                            onNavigateToRegistro = { navController.navigate("registro") },
                            onLoginSuccess = {
                                val esValido = loginViewModel.validarLoginPersistente(sessionManager)

                                if (esValido) {
                                    sessionManager.setLoggedIn(true)
                                    navController.navigate("inicio") {
                                        popUpTo("login") { inclusive = true }
                                    }
                                }
                            }
                        )
                    }

                    composable("registro") {
                        val registroViewModel: RegistroViewModel = viewModel()

                        RegistroUi(
                            viewModel = registroViewModel,
                            onRegistroSuccess = {
                                sessionManager.guardarUsuario(
                                    nombre = registroViewModel.formulario.nombre,
                                    correo = registroViewModel.formulario.correo,
                                    contrasena = registroViewModel.formulario.contrasena
                                )

                                navController.navigate("inicio") {
                                    popUpTo("registro") { inclusive = true }
                                }
                            }
                        )
                    }

                    composable("inicio") {
                        val inicioViewModel: InicioViewModel = viewModel()

                        LaunchedEffect(Unit) {
                            inicioViewModel.cargarIncidencias()
                        }

                        InicioUi(
                            viewModel = inicioViewModel,
                            onNavigateToRedactar = { navController.navigate("redactar") },
                            onNavigateToPerfil = { navController.navigate("perfil") },
                            onNavigateToIncidencia = { idIncidencia ->
                                navController.navigate("incidencia/$idIncidencia")
                            },
                            onCerrarSesion = {
                                sessionManager.setLoggedIn(false)
                                navController.navigate("login") {
                                    popUpTo("inicio") { inclusive = true }
                                }
                            },
                            esModoOscuro = esModoOscuro,
                            onCambiarModoOscuro = { nuevoValor ->
                                esModoOscuro = nuevoValor
                            }
                        )
                    }

                    composable("redactar") {
                        val redactarViewModel: RedactViewModel = viewModel()

                        RedactUi(
                            viewModel = redactarViewModel,
                            onNavigateBack = { navController.popBackStack() },
                            onNavigateToPerfil = { navController.navigate("perfil") }
                        )
                    }

                    composable("incidencia/{id}") { backStackEntry ->
                        val id = backStackEntry.arguments?.getString("id") ?: ""
                        val viewModel = remember { IncidenciaViewModel(application, id) }

                        IncidenciaUi(
                            viewModel = viewModel,
                            onNavigateBack = { navController.popBackStack() }
                        )
                    }

                    composable("perfil") {
                        val perfilViewModel: PerfilViewModel = viewModel()

                        PerfilUi(
                            viewModel = perfilViewModel,
                            onNavigateBack = { navController.popBackStack() },
                            onNavigateToEditar = { navController.navigate("edit") },
                            onNavigateToIncidencia = { idIncidencia -> navController.navigate("incidencia/$idIncidencia") }
                        )
                    }

                    composable("edit") {
                        val editViewModel: EditViewModel = viewModel()

                        EditUi(
                            viewModel = editViewModel,
                            onNavigateBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}