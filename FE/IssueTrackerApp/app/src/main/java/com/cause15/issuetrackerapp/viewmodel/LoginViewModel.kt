package com.cause15.issuetrackerapp.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cause15.issuetrackerapp.data.controller.ProjectAPIService
import com.cause15.issuetrackerapp.data.dto.LoginDTO
import com.cause15.issuetrackerapp.data.controller.UserAPIService
import com.cause15.issuetrackerapp.data.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userAPIService: UserAPIService,
    private val projectAPIService: ProjectAPIService
) : ViewModel() {
    private val _username = mutableStateOf("dev1")
    private val _password = mutableStateOf("123")
    private val _isPasswordVisible = mutableStateOf(false)
    private val _loginResult = mutableStateOf<User?>(null)
    private val _errorMessage = mutableStateOf("")
    val username: State<String> = _username
    val password: State<String> = _password
    val isPasswordVisible: State<Boolean> = _isPasswordVisible
    val loginResult: State<User?> = _loginResult
    val errorMessage: State<String> = _errorMessage

    fun setUsername(value: String) {
        _username.value = value
    }

    fun setPassword(value: String) {
        _password.value = value
    }

    fun login(func: () -> Unit) {
        if (username.value.isEmpty() || password.value.isEmpty()) {
            _errorMessage.value = "Error: Username and password cannot be empty."
            return
        }

        viewModelScope.launch {
            val response1 = userAPIService.login(LoginDTO(username.value, password.value))

            if (response1.isSuccessful) {
                val body1 = response1.body()
                if (body1 != null) {
                    val response2 = projectAPIService.isUserAvailable(body1.id)

                    if (response2.isSuccessful) {
                        val body2 = response2.body()
                        if (body2 != null) {
                            if (body2 == true) {
                                _loginResult.value = body1
                                func()
                                return@launch
                            }
                        }
                    }
                    _errorMessage.value = "Error: Cannot check whether user is in project."
                }
            } else {
                if (response1.code() == 400) _errorMessage.value = "Error: Incorrect password."
                else if (response1.code() == 404) _errorMessage.value = "Error: User not found."
                else _errorMessage.value = "Error: Unknown error."
            }
        }
    }
}