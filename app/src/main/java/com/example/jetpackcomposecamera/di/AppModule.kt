package com.example.jetpackcomposecamera.di

import android.content.Context
import androidx.room.Room
import com.example.jetpackcomposecamera.data.local.dao.ImageDao
import com.example.jetpackcomposecamera.data.local.database.ImageDatabase
import com.example.jetpackcomposecamera.data.repository.ImageDaoRepository
import com.example.jetpackcomposecamera.data.repository.ImageDaoRepositoryImpl
import com.example.jetpackcomposecamera.presentation.camera_screen.viewmodel.CameraViewModelFactory
import com.example.jetpackcomposecamera.presentation.main_screen.viewmodel.MainScreenViewModelFactory
import com.example.jetpackcomposecamera.util.Constant.DATABASE_NAME
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule(val context: Context) {

    @Provides
    @Singleton
    fun provideContext(): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): ImageDatabase {
        return Room.databaseBuilder(context, ImageDatabase::class.java, DATABASE_NAME).build()
    }

    @Provides
    @Singleton
    fun provideItemDao(imageDatabase: ImageDatabase):ImageDao{
        return  imageDatabase.imageDao()
    }

    @Provides
    @Singleton
    fun provideImageRepository(imageDao: ImageDao): ImageDaoRepository {
        return ImageDaoRepositoryImpl(imageDao)
    }

    @Provides
    @Singleton
    fun provideMainScreenViewModelFactory(imageDaoRepository: ImageDaoRepository): MainScreenViewModelFactory{
        return MainScreenViewModelFactory(imageDaoRepository)
    }

    @Provides
    @Singleton
    fun provideCameraViewViewModelFactory(imageDaoRepository: ImageDaoRepository): CameraViewModelFactory{
        return CameraViewModelFactory(imageDaoRepository)
    }
}