package com.example.jetpackcomposecamera.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "image_table")
data class ImageModel(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val user_name: String,
    val image_name: String,
    val image_time: String,
    val image_dir: String
)