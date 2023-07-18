package com.example.jetpackcomposecamera.data.repository


import com.example.jetpackcomposecamera.data.local.dao.ImageDao
import com.example.jetpackcomposecamera.data.model.ImageModel
import javax.inject.Inject

// ImageDaoRepository arayüzünü uygulayan bir sınıftır burası
// Repository tasarım kalıbı olarak bilinir.
// Bu kalıp, veri kaynaklarına erişimi soyutlar ve bu erişimi kolayca değiştirilebilir hale getirir.
// Bu sayede, veri kaynakları kolayca değiştirilebilir ve test edilebilir.

// @Inject anotasyonuyla dagger’in bu sınıfın bir örneğini oluştururken bağımlılıklarını otomatik olarak enjekte etmesini sağlar.
class ImageDaoRepositoryImpl @Inject constructor(
    private val imageDao: ImageDao
):ImageDaoRepository {

    override suspend fun getImagesByNameAsc(): List<ImageModel> {
        return try {
            val imageList = imageDao.getImagesByNameAsc()
            imageList
        }catch (e:Exception){
            TODO()
        }
    }
    override suspend fun deleteImage(imageModel: ImageModel) {
        imageDao.deleteImage(image = imageModel)
    }
    override suspend fun insertImage(imageModel: ImageModel) {
        imageDao.insertImage(image =imageModel )

    }
}