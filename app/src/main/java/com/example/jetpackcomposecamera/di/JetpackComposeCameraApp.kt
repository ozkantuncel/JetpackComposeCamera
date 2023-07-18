package com.example.jetpackcomposecamera.di

import android.app.Application


// @HiltAndroidApp anotasyonu, uygulamanızın Application sınıfının Hilt tarafından yönetilmesini sağlar.
// AndroidManifest.xml de uygulamanızın Application sınıfının adını belirtmeniz gerekir
// Bu anotasyon, Hilt'in uygulamanızın tüm bağımlılıkları için otomatik olarak bir bağımlılık yöneticisi oluşturmasını sağlar kısaca.

class JetpackComposeCameraApp : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        applicationComponent =
            DaggerApplicationComponent.builder().appModule(AppModule(context = this)).build()
    }
}