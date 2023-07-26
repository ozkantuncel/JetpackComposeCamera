package com.example.jetpackcomposecamera.presentation.navigation

sealed class Screen(val route: String){
    object MainScreen: Screen(route = "main_screen")
    object CameraView: Screen(route = "camera_screen")

    object LoginScreen: Screen(route = "login_screen")

    object RegisterScreen: Screen(route = "register_screen")

    object OnBoardingScreen: Screen(route = "on_boarding_screen")
}