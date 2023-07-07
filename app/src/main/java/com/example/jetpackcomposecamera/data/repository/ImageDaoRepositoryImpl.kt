package com.example.jetpackcomposecamera.data.repository

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.jetpackcomposecamera.data.local.database.ImageDatabase
import com.example.jetpackcomposecamera.data.model.ImageModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ImageDaoRepositoryImpl(var application: Application):ImageDaoRepository {

    var getImageList: MutableLiveData<List<ImageModel>> = MutableLiveData()

    var database: ImageDatabase = ImageDatabase.getDatabase(application)

    override suspend fun getImagesByNameAsc(): List<ImageModel> {
        return try {
            val myList = database.imageDao().getImagesByNameAsc()
            myList
        }catch (e: Exception){
            TODO()
        }
    }

    override suspend fun deleteImage(imageModel: ImageModel) {
        withContext(Dispatchers.IO){
            database.imageDao().deleteImage(image = imageModel)
        }
    }

    override suspend fun insertImage(imageModel: ImageModel) {
        withContext(Dispatchers.IO){
            database.imageDao().insertImage(image =imageModel )
        }
    }

    private fun getImages(){
        CoroutineScope(Dispatchers.Main).launch {
            getImageList.value = database.imageDao().getImagesByNameAsc()
        }
    }
}