package com.example.jetpackcomposecamera.presentation.main_screen.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposecamera.data.model.ImageModel
import com.example.jetpackcomposecamera.data.repository.ImageDaoRepositoryImpl
import kotlinx.coroutines.launch
import java.io.File

class MainScreenViewModel(application: Application):AndroidViewModel(application){
    private val repo = ImageDaoRepositoryImpl(getApplication())
    // LiveData olarak tanımlanan _listImage değişkeni, dışarıdan erişime açık değildir
    // LiveData, veri değişikliklerini otomatik olarak takip eden ve güncelleyen bir yapıdır
    private var _listImage =  MutableLiveData<List<ImageModel>>()
    // listImage değişkeni, dışarıdan erişilebilir LiveData olarak tanımlı
    val listImage: LiveData<List<ImageModel>> = _listImage
    // İlk olarak fetchData() fonksiyonunu çağırarak verileri getirir
    init {
        fetchData()
    }
    private fun fetchData(){
        viewModelScope.launch {
            val result = repo.getImagesByNameAsc()
            _listImage.value = result
        }
    }

    fun deleteData(imageModel: ImageModel){
        viewModelScope.launch {
            repo.deleteImage(imageModel = imageModel)
            fetchData()
            File(imageModel.image_dir).delete()
        }
    }

}