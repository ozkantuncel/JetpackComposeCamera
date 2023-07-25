package com.example.jetpackcomposecamera.data.repository

import com.example.jetpackcomposecamera.data.model.UserModel
import com.example.jetpackcomposecamera.util.UiState

interface UserDaoRepository {

    suspend fun getUserByUsernameAndPassword(username:String,password: String):UiState<UserModel>

    suspend fun insertUser(userModel: UserModel)

    suspend fun checkUsernameExistence(username: String)

}