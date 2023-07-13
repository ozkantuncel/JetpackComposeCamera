package com.example.jetpackcomposecamera.presentation.main_screen.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposecamera.data.model.ImageModel
import com.example.jetpackcomposecamera.data.repository.ImageDaoRepositoryImpl
import kotlinx.coroutines.launch

class MainScreenViewModel(application: Application):AndroidViewModel(application){
    private val repo = ImageDaoRepositoryImpl(application)

    private var _listImage =  MutableLiveData<List<ImageModel>>()
    val listImage: LiveData<List<ImageModel>> = _listImage


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
        }
    }
}