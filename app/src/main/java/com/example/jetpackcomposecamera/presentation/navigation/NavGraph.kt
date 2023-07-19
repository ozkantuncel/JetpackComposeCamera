package com.example.jetpackcomposecamera.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.jetpackcomposecamera.presentation.camera_screen.CameraViewScreen
import com.example.jetpackcomposecamera.presentation.main_screen.MainScreen

@Composable
fun NavGraph(
    navHostController: NavHostController,
    startDestination: String,
) {
    // NavHost bileşeni
    NavHost(
        navController = navHostController,
        // startDestination özelliğine başlangıç hedefi
        startDestination = startDestination,
    ) {
        composable(route = Screen.MainScreen.route){
            MainScreen(navController = navHostController)
        }
        composable(route = Screen.CameraView.route){
            CameraViewScreen(navController = navHostController)
        }
    }
}
