package com.example.jetpackcomposecamera.presentation.auth_screens.register_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposecamera.data.model.UserModel
import com.example.jetpackcomposecamera.data.repository.UserDaoRepository
import com.example.jetpackcomposecamera.util.UiState
import com.example.jetpackcomposecamera.util.Constant.isStrongPassword
import com.example.jetpackcomposecamera.util.hawk.Prefs.setUsername
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterScreenViewModel @Inject constructor(
    private val repository: UserDaoRepository
): ViewModel() {

    private var _user = MutableLiveData<UiState<String>>()
    val user: LiveData<UiState<String>> = _user

    fun insertUser(userModel: UserModel){
        if(isStrongPassword(password = userModel.user_password)){
            _user.value = UiState.Loading
            viewModelScope.launch {
                repository.insertUser(userModel)
                setUsername(userModel.user_name)
                _user.value = UiState.Success("Suc")
            }
        }else{
            _user.value = UiState.Failure("Sifre yetersiz")
        }
    }
}