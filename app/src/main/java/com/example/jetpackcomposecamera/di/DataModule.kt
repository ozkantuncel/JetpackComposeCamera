package com.example.jetpackcomposecamera.di

import android.content.Context
import androidx.room.Room
import com.example.jetpackcomposecamera.data.local.dao.ImageDao
import com.example.jetpackcomposecamera.data.local.database.ImageDatabase
import com.example.jetpackcomposecamera.util.Constant
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

// Module anotasyonu ile bu sınıfın bir Dagger modülü olduğunu belirtiyor.
// AppModule modülünü bu modüle dahil ediliyor.
@Module(includes = [AppModule::class])
class DataModule {
    // Provides anotasyonu ile bu fonksiyonun bir bağımlılık sağlayacağını belirtiyor.
    // Singleton anotasyonu ile bu component'in uygulama boyunca tek bir instance'ı olacağını belirtiyor.
    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): ImageDatabase {
        // Room kütüphanesi ile bir ImageDatabase nesnesi oluşturuyoruz ve döndürüyor.
        return Room.databaseBuilder(context, ImageDatabase::class.java, Constant.DATABASE_NAME).build()
    }

    // ImageDatabase nesnesinden bir ImageDao nesnesi elde edip ve döndürüyor.
    @Provides
    @Singleton
    fun provideItemDao(imageDatabase: ImageDatabase): ImageDao = imageDatabase.imageDao()

}
