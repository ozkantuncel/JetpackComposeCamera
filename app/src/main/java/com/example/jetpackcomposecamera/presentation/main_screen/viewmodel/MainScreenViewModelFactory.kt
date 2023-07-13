package com.example.jetpackcomposecamera.presentation.main_screen.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class MainScreenViewModelFactory(var application: Application): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainScreenViewModel::class.java)) {
            return MainScreenViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}