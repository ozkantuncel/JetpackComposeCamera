package com.example.jetpackcomposecamera.presentation.camera_screen.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

// CameraViewModelFactory sınıfı ViewModelProvider.NewInstanceFactory sınıfından kalıtım alır
// ViewModelProvider, ViewModel sınıflarının bağımlılıklarını yönetmeyi sağlar.
class CameraViewModelFactory(var application: Application):ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CameraViewModel::class.java)) {
            return CameraViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}