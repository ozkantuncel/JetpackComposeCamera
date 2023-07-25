package com.example.jetpackcomposecamera.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserModel(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val user_name: String,
    val user_password: String,
)