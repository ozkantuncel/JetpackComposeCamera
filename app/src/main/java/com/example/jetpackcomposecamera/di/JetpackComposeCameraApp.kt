package com.example.jetpackcomposecamera.di

import android.app.Application


// AndroidManifest.xml de uygulamanızın Application sınıfının adını belirtmeniz gerekir
class JetpackComposeCameraApp : Application() {
    // Companion object içinde bir ApplicationComponent nesnesi tanımlı.
    companion object {
        lateinit var applicationComponent: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()
        // ApplicationComponent nesnesini DaggerApplicationComponent builder'ını kullanarak oluşturuyor.
        // AppModule modülünü de dahil ediyor ve context olarak uygulama context'ini veriyoruz.
        applicationComponent =
            DaggerApplicationComponent.builder().appModule(AppModule(context = this)).build()
    }
}