package com.example.jetpackcomposecamera.presentation.main_screen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jetpackcomposecamera.data.repository.ImageDaoRepository
import javax.inject.Inject

// MainScreenViewModelFactory sınıfı ViewModelProvider.NewInstanceFactory sınıfından kalıtım alır
// ViewModelProvider, ViewModel sınıflarının bağımlılıklarını yönetmeyi sağlar.
class MainScreenViewModelFactory @Inject constructor(private  val repository: ImageDaoRepository): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainScreenViewModel::class.java)) {
            return MainScreenViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}