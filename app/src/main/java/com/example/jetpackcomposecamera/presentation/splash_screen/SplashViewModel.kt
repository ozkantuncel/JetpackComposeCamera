package com.example.jetpackcomposecamera.presentation.splash_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposecamera.presentation.navigation.Screen
import com.example.jetpackcomposecamera.util.hawk.Prefs
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashViewModel:ViewModel() {

    private val _isLoading: MutableState<Boolean> = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    private val _startDestination: MutableState<String> = mutableStateOf(Screen.LoginScreen.route)
    val startDestination: State<String> = _startDestination

    init {
        viewModelScope.launch {

            if(getOnBoardingScreen()){
                if(getRememberMeState()){
                    _startDestination.value = Screen.MainScreen.route
                }else{
                    _startDestination.value = Screen.LoginScreen.route
                }
            }else{
                _startDestination.value = Screen.OnBoardingScreen.route
            }


            delay(1000)
            _isLoading.value = false
        }
    }



    private fun getRememberMeState(): Boolean {
        return Prefs.isStayIn()
    }

    private fun getOnBoardingScreen():Boolean{
        return Prefs.isShowedOnBoarding()
    }
}