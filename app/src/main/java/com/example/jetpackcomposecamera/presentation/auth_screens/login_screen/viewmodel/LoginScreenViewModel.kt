package com.example.jetpackcomposecamera.presentation.auth_screens.login_screen.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposecamera.data.model.UserModel
import com.example.jetpackcomposecamera.data.repository.UserDaoRepository
import com.example.jetpackcomposecamera.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val repository: UserDaoRepository
) : ViewModel() {

    private var _user = MutableLiveData<UiState<UserModel>>()
    val user: LiveData<UiState<UserModel>> = _user

    fun fetchUser(username: String, password: String) {
        _user.value = UiState.Loading
        viewModelScope.launch {
            try {
                val result = repository.getUserByUsernameAndPassword(username, password)
                _user.value = result
            } catch (e: Exception) {
                _user.value = UiState.Failure(e.localizedMessage)
            }
        }
    }
}