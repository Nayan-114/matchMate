package com.example.metrimonialapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.metrimonialapp.ui.MainScreen
import com.example.metrimonialapp.ui.theme.MetrimonialAppTheme
import com.example.metrimonialapp.viewmodel.UserViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MetrimonialAppTheme {
                val vm: UserViewModel = viewModel()
                MainScreen(viewModel = vm)
            }
        }
    }
}