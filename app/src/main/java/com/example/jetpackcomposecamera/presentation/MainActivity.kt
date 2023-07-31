package com.example.jetpackcomposecamera.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.example.jetpackcomposecamera.presentation.navigation.NavGraph
import com.example.jetpackcomposecamera.presentation.splash_screen.SplashViewModel
import com.example.jetpackcomposecamera.presentation.ui.theme.JetpackComposeCameraTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val splashViewModel: SplashViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition{
            !splashViewModel.isLoading.value
        }
        setContent {
            JetpackComposeCameraTheme {
                val screen by splashViewModel.startDestination
                val navController = rememberNavController()
                NavGraph(
                    navHostController = navController,
                    startDestination = screen
                )
            }
        }
    }



}