package com.example.jetpackcomposecamera.presentation.camera_screen.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposecamera.data.model.ImageModel
import com.example.jetpackcomposecamera.data.repository.ImageDaoRepository
import kotlinx.coroutines.launch


// MVVM (Model-View-ViewModel) desenine uygundur
// ViewModel, Activity, Fragment ve Composable fonksiyonlarla gibi yaşam döngüsüne sahip bileşenlerle veri paylaşımını sağlar.

class CameraViewModel(private val repository: ImageDaoRepository) : ViewModel() {

    // viewModelScope.launch{} bloğu, bir coroutine scope'u oluşturur ve bu scope içindeki işlemler asenkron olarak çalışır.
    fun insertData(imageModel: ImageModel) {
        viewModelScope.launch {
            repository.insertImage(imageModel)
        }
    }
}