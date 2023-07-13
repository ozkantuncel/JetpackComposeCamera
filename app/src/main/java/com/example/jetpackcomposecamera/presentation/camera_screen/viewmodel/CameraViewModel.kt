package com.example.jetpackcomposecamera.presentation.camera_screen.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposecamera.data.model.ImageModel
import com.example.jetpackcomposecamera.data.repository.ImageDaoRepositoryImpl
import kotlinx.coroutines.launch

class CameraViewModel(application: Application) : AndroidViewModel(application) {
    private val repo = ImageDaoRepositoryImpl(application)


    fun insertData(imageModel: ImageModel){
        viewModelScope.launch {
            repo.insertImage(imageModel)
        }
    }

}