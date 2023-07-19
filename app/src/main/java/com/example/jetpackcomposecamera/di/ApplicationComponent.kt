package com.example.jetpackcomposecamera.di

import com.example.jetpackcomposecamera.presentation.camera_screen.viewmodel.CameraViewModel
import com.example.jetpackcomposecamera.presentation.main_screen.viewmodel.MainScreenViewModel
import dagger.Component
import javax.inject.Singleton


// Singleton anotasyonu ile bu component'in uygulama boyunca tek bir instance'ı olacağını belirtiyor.
// Component anotasyonu ile bu sınıfın bir Dagger component'i olduğunu belirtiyor.
@Singleton
@Component(modules = [AppModule::class, DataModule::class])
interface ApplicationComponent {
    fun inject(cameraViewModel:CameraViewModel)
    fun inject(mainScreenViewModel: MainScreenViewModel)
}