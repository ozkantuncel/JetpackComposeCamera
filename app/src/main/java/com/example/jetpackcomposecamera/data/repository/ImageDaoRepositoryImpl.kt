package com.example.jetpackcomposecamera.data.repository


import com.example.jetpackcomposecamera.data.local.dao.ImageDao
import com.example.jetpackcomposecamera.data.model.ImageModel
import javax.inject.Inject

// ImageDaoRepository arayüzünü uygulayan bir sınıftır burası
// Repository tasarım kalıbı olarak bilinir.
// Bu kalıp, veri kaynaklarına erişimi soyutlar ve bu erişimi kolayca değiştirilebilir hale getirir.
// Bu sayede, veri kaynakları kolayca değiştirilebilir ve test edilebilir.

class ImageDaoRepositoryImpl @Inject constructor(
    private val imageDao: ImageDao
) :ImageDaoRepository {


    override suspend fun getImagesByNameAsc(): List<ImageModel> {
        return try {
            val imageList = imageDao.getImagesByNameAsc()

            val sortedList = imageList.groupBy { it.user_name }
                .mapValues { it.value.size }
                .toList()
                .sortedByDescending { it.second }
                .flatMap { entry -> imageList.filter { it.user_name == entry.first } }

            sortedList
        } catch (e: Exception) {
            TODO()
        }
    }

    override suspend fun deleteImage(imageModel: ImageModel) {
        imageDao.deleteImage(image = imageModel)
    }

    override suspend fun insertImage(imageModel: ImageModel) {
        imageDao.insertImage(image = imageModel)


    }
    override suspend fun deleteAll(username:String) {
        imageDao.deleteAll(username = username)
    }
}