package com.cause15.issuetrackerapp.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cause15.issuetrackerapp.data.dto.LoginDTO
import com.cause15.issuetrackerapp.data.controller.UserAPIService
import com.cause15.issuetrackerapp.data.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userAPIService: UserAPIService
) : ViewModel() {
    private val _username = mutableStateOf("")
    private val _password = mutableStateOf("")
    private val _isPasswordVisible = mutableStateOf(false)
    private val _loginResult = mutableStateOf<User?>(null)
    private val _errorCode = mutableIntStateOf(-1)
    val username: State<String> = _username
    val password: State<String> = _password
    val isPasswordVisible: State<Boolean> = _isPasswordVisible
    val loginResult: State<User?> = _loginResult
    val errorCode: State<Int> = _errorCode

    fun setUsername(value: String) {
        _username.value = value
    }

    fun setPassword(value: String) {
        _password.value = value
    }

    fun login() {
        viewModelScope.launch {
            val response = userAPIService.login(LoginDTO(username.value, password.value))

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) _loginResult.value = body
            } else {
                _errorCode.intValue = response.code()
            }
        }
    }
}