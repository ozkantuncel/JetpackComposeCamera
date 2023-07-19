package com.example.jetpackcomposecamera.presentation.camera_screen.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposecamera.data.model.ImageModel
import com.example.jetpackcomposecamera.data.repository.ImageDaoRepositoryImpl
import com.example.jetpackcomposecamera.di.JetpackComposeCameraApp
import kotlinx.coroutines.launch
import javax.inject.Inject


// MVVM (Model-View-ViewModel) desenine uygundur
// ViewModel, Activity, Fragment ve Composable fonksiyonlarla gibi yaşam döngüsüne sahip bileşenlerle veri paylaşımını sağlar.

class CameraViewModel() : ViewModel() {

    // init bloğu, sınıfın constructor'ı çağrıldığında çalışır.
    @Inject
    lateinit var repository: ImageDaoRepositoryImpl

    init {
        // ApplicationComponent nesnesini kullanarak bu sınıfa bağımlılıkları enjekte ediyoruz.
        JetpackComposeCameraApp.applicationComponent.inject(this)
    }

    // viewModelScope.launch{} bloğu, bir coroutine scope'u oluşturur ve bu scope içindeki işlemler asenkron olarak çalışır.
    fun insertData(imageModel: ImageModel) {
        // viewModelScope.launch{} bloğu, bir coroutine scope'u oluşturur ve bu scope içindeki işlemler asenkron olarak çalışır.
        viewModelScope.launch {
            repository.insertImage(imageModel)
        }
    }
}