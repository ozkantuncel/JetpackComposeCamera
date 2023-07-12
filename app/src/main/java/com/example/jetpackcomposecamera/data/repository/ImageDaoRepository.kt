package com.example.jetpackcomposecamera.data.repository

import com.example.jetpackcomposecamera.data.model.ImageModel

interface ImageDaoRepository {

    suspend fun getImagesByNameAsc(): List<ImageModel>

    suspend fun deleteImage(imageModel: ImageModel)

    suspend fun insertImage(imageModel: ImageModel)
}