package com.example.jetpackcomposecamera.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.jetpackcomposecamera.presentation.auth_screens.login_screen.LoginScreen
import com.example.jetpackcomposecamera.presentation.auth_screens.register_screen.RegisterScreen
import com.example.jetpackcomposecamera.presentation.camera_screen.CameraView
import com.example.jetpackcomposecamera.presentation.main_screen.MainScreen
import com.example.jetpackcomposecamera.presentation.on_boarding_screen.OnBoardingScreen

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
            CameraView(navController = navHostController)
        }

        composable(route = Screen.RegisterScreen.route){
            RegisterScreen(navController = navHostController)
        }

        composable(route = Screen.LoginScreen.route){
            LoginScreen(navController = navHostController)
        }

        composable(route = Screen.OnBoardingScreen.route){
            OnBoardingScreen(navController = navHostController)
        }
    }
}
