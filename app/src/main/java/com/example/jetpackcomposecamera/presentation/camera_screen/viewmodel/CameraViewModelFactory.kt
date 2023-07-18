package com.example.jetpackcomposecamera.presentation.camera_screen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jetpackcomposecamera.data.repository.ImageDaoRepository
import javax.inject.Inject

// CameraViewModelFactory sınıfı ViewModelProvider.NewInstanceFactory sınıfından kalıtım alır
// ViewModelProvider, ViewModel sınıflarının bağımlılıklarını yönetmeyi sağlar.
class CameraViewModelFactory @Inject constructor(private val repository: ImageDaoRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CameraViewModel::class.java)) {
            return CameraViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}