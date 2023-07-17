package com.example.jetpackcomposecamera.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// Veritabanında tabloları temsil eden bir veri sınıfı
// tableName: Tablonun adını belirtir
@Entity(tableName = "image_table")
data class ImageModel(
    // PrimaryKey: Otomatik şekilde keyleri oluşturan bir etiket
    // autoGenerate: Anahtarın otomatik olarak oluşturulup oluşturulmayacağını belirtir
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val user_name: String,
    val image_name: String,
    val image_time: String,
    val image_dir: String
)