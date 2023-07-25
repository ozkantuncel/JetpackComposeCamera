package com.example.jetpackcomposecamera.data.repository


import com.example.jetpackcomposecamera.data.local.dao.UserDao
import com.example.jetpackcomposecamera.data.model.UserModel
import com.example.jetpackcomposecamera.util.UiState
import com.example.jetpackcomposecamera.util.hawk.Prefs.setUsername
import java.lang.Exception
import javax.inject.Inject

class UserDaoRepositoryImpl @Inject constructor(
    private val userDao: UserDao
):UserDaoRepository {

    override suspend fun getUserByUsernameAndPassword(username:String,password: String): UiState<UserModel> {
        return try {

            val user= userDao.getUserByUsernameAndPassword(username, password)//UserModel
            if(user != null){
                setUsername(user.user_name)
                UiState.Success(user)
            }else{
                UiState.Failure(null)
            }
        }catch (e:Exception){
            UiState.Failure(e.localizedMessage)
        }
    }

    override suspend fun insertUser(userModel: UserModel) {
        userDao.insertUser(userModel)
    }

    override suspend fun checkUsernameExistence(username: String) {
        userDao.checkUsernameExistence(username = username)
    }


}