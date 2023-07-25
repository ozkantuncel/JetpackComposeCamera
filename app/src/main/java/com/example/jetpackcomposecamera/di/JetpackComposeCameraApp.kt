package com.example.jetpackcomposecamera.di


import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class JetpackComposeCameraApp:CoreLibApplication(){
    init {
        instance = this
    }
    companion object{
        lateinit var instance:JetpackComposeCameraApp

        fun applicationContext(): Context {
            return instance.applicationContext
        }
    }
}