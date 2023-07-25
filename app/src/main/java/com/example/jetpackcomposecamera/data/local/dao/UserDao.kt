package com.example.jetpackcomposecamera.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.jetpackcomposecamera.data.model.UserModel

@Dao
interface UserDao {

    @Query("SELECT * FROM user_table WHERE user_name = :username AND user_password = :password")
    suspend fun getUserByUsernameAndPassword(username: String, password: String): UserModel?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: UserModel)

    @Query("SELECT COUNT(*) FROM user_table WHERE user_name = :username")
    fun checkUsernameExistence(username: String): Boolean
}