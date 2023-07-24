package com.example.jetpackcomposecamera.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.jetpackcomposecamera.data.local.dao.ImageDao
import com.example.jetpackcomposecamera.data.model.ImageModel



// entities: Veritabanında bulunan tablolar
// version: Veritabanı sürümünü
// exportSchema: Veritabanı şemasının dışa aktarılıp aktarılmayacağını belirtir
@Database(entities = [ImageModel::class], version = 1, exportSchema = false)
abstract class ImageDatabase : RoomDatabase() {

    // ImageDao arayüzünü döndüren soyut bir metot
    // Bu metot, veritabanı işlemlerini gerçekleştirmek için kullanılır
    abstract fun imageDao(): ImageDao

    /*companion object {
        // Veritabanı örneğini tutan bir statik bir değişken
        // @Volatile anahtar kelimesi, bu değişkenin birden fazla iş parçacığı tarafından erişilebileceğini belirtir
        @Volatile
        private var INSTANCE: ImageDatabase? = null

        // Veritabanı döndüren bir fonksiyon
        // Eğer veritabanı daha önce oluşturulmamışsa, bu fonksiyon yeni bir veritabanı oluşturur
        fun getDatabase(context: Context): ImageDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ImageDatabase::class.java,
                    DATABASE_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }*/
}