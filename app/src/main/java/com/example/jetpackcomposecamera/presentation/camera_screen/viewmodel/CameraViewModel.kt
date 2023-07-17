package com.example.jetpackcomposecamera.presentation.camera_screen.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposecamera.data.model.ImageModel
import com.example.jetpackcomposecamera.data.repository.ImageDaoRepositoryImpl
import kotlinx.coroutines.launch


// MVVM (Model-View-ViewModel) desenine uygundur
// ViewModel, Activity, Fragment ve Composable fonksiyonlarla gibi yaşam döngüsüne sahip bileşenlerle veri paylaşımını sağlar.
class CameraViewModel(application: Application) : AndroidViewModel(application) {
    private val repo = ImageDaoRepositoryImpl(getApplication())


    // viewModelScope.launch{} bloğu, bir coroutine scope'u oluşturur ve bu scope içindeki işlemler asenkron olarak çalışır.
    fun insertData(imageModel: ImageModel){
        viewModelScope.launch {
            repo.insertImage(imageModel)
        }
    }
}