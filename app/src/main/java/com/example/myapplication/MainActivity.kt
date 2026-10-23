package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.myapplication.ui.RegistroUi
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.theme.LoginUi
import com.example.myapplication.viewmodel.LoginViewModel
import com.example.myapplication.viewmodel.RegistroViewModel

class MainActivity : ComponentActivity() {

    private val loginModelForm = LoginViewModel()
    private val registroModelForm = RegistroViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                LoginUi(loginModelForm)
                //RegistroUi(registroModelForm)
            }
        }
    }
}


