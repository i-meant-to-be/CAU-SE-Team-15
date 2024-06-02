package com.cause15.issuetrackerapp.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.hilt.navigation.compose.hiltViewModel
import com.cause15.issuetrackerapp.viewmodel.LoginViewModel

@Composable
fun LoginView(
    modifier: Modifier,
    viewModel: LoginViewModel = hiltViewModel()
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = viewModel.username.value,
                label = { Text("Username") },
                singleLine = true,
                onValueChange = { newValue -> viewModel.setUsername(newValue) }
            )
            OutlinedTextField(
                value = viewModel.password.value,
                label = { Text("Password") },
                singleLine = true,
                visualTransformation = if (viewModel.isPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                onValueChange = { newValue -> viewModel.setPassword(newValue) }
            )
            Button(onClick = { viewModel.login() }) {
                Text("Login")
            }
            Text(text = viewModel.loginResult.value.toString())
        }
    }
}