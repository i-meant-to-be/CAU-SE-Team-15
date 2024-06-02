package com.cause15.issuetrackerapp.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {
    private val _username = mutableStateOf("")
    private val _password = mutableStateOf("")
    private val _isPasswordVisible = mutableStateOf(false)
    val username: State<String> = _username
    val password: State<String> = _password
    val isPasswordVisible: State<Boolean> = _isPasswordVisible

    fun setUsername(value: String) {
        _username.value = value
    }

    fun setPassword(value: String) {
        _password.value = value
    }
}