package com.example.jetpackcomposecamera.di

import android.content.Context
import androidx.room.Room
import com.example.jetpackcomposecamera.data.local.dao.ImageDao
import com.example.jetpackcomposecamera.data.local.dao.UserDao
import com.example.jetpackcomposecamera.data.local.database.ImageDatabase
import com.example.jetpackcomposecamera.util.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RoomModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): ImageDatabase =
        Room.databaseBuilder(
            context,
            ImageDatabase::class.java,
            Constant.DATABASE_NAME
        ).build()

    @Provides
    @Singleton
    fun provideImageDataDao(database: ImageDatabase): ImageDao = database.imageDao()

    @Provides
    @Singleton
    fun provideUserDao(database: ImageDatabase):UserDao = database.userDao()
}