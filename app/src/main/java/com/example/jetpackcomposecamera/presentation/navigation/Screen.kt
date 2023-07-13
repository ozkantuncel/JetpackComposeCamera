package com.example.jetpackcomposecamera.presentation.navigation

sealed class Screen(val route: String){
    object MainScreen: Screen(route = "main_screen")
    object CameraView: Screen(route = "camera_screen")
}