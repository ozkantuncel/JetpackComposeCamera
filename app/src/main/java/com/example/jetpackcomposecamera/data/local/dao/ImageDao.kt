package com.example.jetpackcomposecamera.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.jetpackcomposecamera.data.model.ImageModel

/*
    Bu arayüz, bir DAO  olarak kullanılır ve veritabanı
    işlemlerini soyutlar. Bu sayede, veritabanı işlemleri kolayca
    değiştirilebilir ve test edilebilir.
     */
@Dao
interface ImageDao {
    //  Resimleri isimlerine göre sıralayarak sorgular
    @Query("SELECT * FROM image_table ORDER BY image_name ASC")
    suspend fun getImagesByNameAsc(): List<ImageModel>

    // Bir resmi veritabanına ekler
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(image: ImageModel)

    // Bir resmi veritabanından siler
    @Delete
    suspend fun deleteImage(image: ImageModel)

    /*
    suspend anahtar vardır,
    bu metotların asekron olarak çalıştığını ve bir
    CoroutineScope içinde çağrılması gerektiğini anlamına gelir.
     */
}