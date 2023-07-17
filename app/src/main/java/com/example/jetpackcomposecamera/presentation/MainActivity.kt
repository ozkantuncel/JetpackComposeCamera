package com.example.jetpackcomposecamera.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.jetpackcomposecamera.presentation.navigation.NavGraph
import com.example.jetpackcomposecamera.presentation.navigation.Screen
import com.example.jetpackcomposecamera.presentation.ui.theme.JetpackComposeCameraTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeCameraTheme {
                val navController = rememberNavController()

                NavGraph(
                    navHostController = navController,
                    startDestination = Screen.MainScreen.route,
                )
            }
        }
    }
}





