package com.example.jetpackcomposecamera.di

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

// Module anotasyonu ile bu sınıfın bir Dagger modülü olduğunu belirtiyor.
@Module
class AppModule(val context: Context) {
    // Provides anotasyonu ile bu fonksiyonun bir bağımlılık sağlayacağını belirtiyor.
    // Singleton anotasyonu ile bu component'in uygulama boyunca tek bir instance'ı olacağını belirtiyor.
    // Context nesnesini döndürüyor.
    @Provides
    @Singleton
    fun provideContext(): Context = context
}