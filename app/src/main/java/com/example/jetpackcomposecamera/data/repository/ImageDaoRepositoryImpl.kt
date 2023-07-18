package com.example.jetpackcomposecamera.data.repository

import android.app.Application
import com.example.jetpackcomposecamera.data.local.database.ImageDatabase
import com.example.jetpackcomposecamera.data.model.ImageModel

// ImageDaoRepository arayüzünü uygulayan bir sınıftır burası
// Repository tasarım kalıbı olarak bilinir.
// Bu kalıp, veri kaynaklarına erişimi soyutlar ve bu erişimi kolayca değiştirilebilir hale getirir.
// Bu sayede, veri kaynakları kolayca değiştirilebilir ve test edilebilir.
class ImageDaoRepositoryImpl(application: Application):ImageDaoRepository {
    private var database: ImageDatabase = ImageDatabase.getDatabase(application)

    override suspend fun getImagesByNameAsc(): List<ImageModel> {
        return try {
            val imageList = database.imageDao().getImagesByNameAsc()
            imageList
        }catch (e:Exception){
            TODO()
        }
    }
    override suspend fun deleteImage(imageModel: ImageModel) {
        database.imageDao().deleteImage(image = imageModel)
    }
    override suspend fun insertImage(imageModel: ImageModel) {
        database.imageDao().insertImage(image =imageModel )
    }

    override suspend fun deleteAll() {
        database.imageDao().deleteAll()
    }
}