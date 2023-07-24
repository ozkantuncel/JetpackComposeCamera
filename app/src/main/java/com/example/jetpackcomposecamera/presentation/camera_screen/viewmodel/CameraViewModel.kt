package com.example.jetpackcomposecamera.presentation.camera_screen.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposecamera.data.model.ImageModel
import com.example.jetpackcomposecamera.data.repository.ImageDaoRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


// MVVM (Model-View-ViewModel) desenine uygundur
// ViewModel, Activity, Fragment ve Composable fonksiyonlarla gibi yaşam döngüsüne sahip bileşenlerle veri paylaşımını sağlar.
@HiltViewModel
class CameraViewModel @Inject constructor(
    private val repository: ImageDaoRepositoryImpl
): ViewModel() {

    // viewModelScope.launch{} bloğu, bir coroutine scope'u oluşturur ve bu scope içindeki işlemler asenkron olarak çalışır.
    fun insertData(imageModel: ImageModel){
        viewModelScope.launch {
            repository.insertImage(imageModel)
        }
    }
}