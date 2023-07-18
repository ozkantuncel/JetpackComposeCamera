package com.example.jetpackcomposecamera.presentation.main_screen.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposecamera.data.model.ImageModel
import com.example.jetpackcomposecamera.data.repository.ImageDaoRepository
import kotlinx.coroutines.launch
import java.io.File

class MainScreenViewModel  (
    private val repository: ImageDaoRepository
):ViewModel(){

    // LiveData olarak tanımlanan _listImage değişkeni, dışarıdan erişime açık değildir
    // LiveData, veri değişikliklerini otomatik olarak takip eden ve güncelleyen bir yapıdır
    private var _listImage =  MutableLiveData<List<ImageModel>>()
    // listImage değişkeni, dışarıdan erişilebilir LiveData olarak tanımlı
    val listImage: LiveData<List<ImageModel>> = _listImage
    // İlk olarak fetchData() fonksiyonunu çağırarak verileri getirir

     fun fetchData(){
        viewModelScope.launch {
            val result = repository.getImagesByNameAsc()
            _listImage.value = result
        }
    }

    fun deleteData(imageModel: ImageModel){
        viewModelScope.launch {
            repository.deleteImage(imageModel = imageModel)
            fetchData()
            File(imageModel.image_dir).delete()
        }
    }
}