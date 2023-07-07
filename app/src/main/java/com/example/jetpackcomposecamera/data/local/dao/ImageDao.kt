package com.example.jetpackcomposecamera.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.jetpackcomposecamera.data.model.ImageModel

@Dao
interface ImageDao {

    @Query("SELECT * FROM image_table ORDER BY image_name ASC")
    suspend fun getImagesByNameAsc():List<ImageModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(image:ImageModel)

    @Delete
    fun deleteImage(image: ImageModel)
}