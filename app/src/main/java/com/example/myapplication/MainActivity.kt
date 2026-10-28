package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.InicioUi
import com.example.myapplication.ui.RedactUi
import com.example.myapplication.ui.RegistroUi

import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.theme.LoginUi
import com.example.myapplication.viewmodel.InicioViewModel
import com.example.myapplication.viewmodel.LoginViewModel
import com.example.myapplication.viewmodel.RedactViewModel
import com.example.myapplication.viewmodel.RegistroViewModel


class MainActivity : ComponentActivity() {

    private val loginModelForm = LoginViewModel()
    private val registroModelForm = RegistroViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme{
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "login") {

                    composable("login") {
                        val loginViewModel: LoginViewModel = viewModel()
                        LoginUi(
                            viewModel = loginViewModel,
                            onNavigateToRegistro = {
                                navController.navigate("registro")
                            },
                            onLoginSuccess = {
                                navController.navigate("inicio"){
                                    popUpTo("login"){
                                        inclusive = true
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
                                navController.navigate("inicio"){
                                    popUpTo("registro") {
                                        inclusive = true
                                    }
                                }
                            }
                        )
                    }

                    composable("inicio") {
                        val inicioViewModel: InicioViewModel = viewModel()
                        InicioUi(
                            inicioViewModel,
                            { navController.navigate("redactar") },
                            { }
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
                }
            }
        }
    }
}

