package com.example.jetpackcomposecamera.di

import com.example.jetpackcomposecamera.presentation.camera_screen.viewmodel.CameraViewModelFactory
import com.example.jetpackcomposecamera.presentation.main_screen.viewmodel.MainScreenViewModelFactory
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class])
interface ApplicationComponent {
    fun mainScreenViewModelFactory():MainScreenViewModelFactory
    fun cameraViewViewModelFactory(): CameraViewModelFactory
}