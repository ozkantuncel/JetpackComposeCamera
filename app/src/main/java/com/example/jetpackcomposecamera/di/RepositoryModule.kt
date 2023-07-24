package com.example.jetpackcomposecamera.di


import com.example.jetpackcomposecamera.data.local.dao.ImageDao
import com.example.jetpackcomposecamera.data.repository.ImageDaoRepository
import com.example.jetpackcomposecamera.data.repository.ImageDaoRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {
    @Provides
    @Singleton
    fun provideImageRepository(
        imageDao: ImageDao
    ): ImageDaoRepository = ImageDaoRepositoryImpl(imageDao = imageDao)
}