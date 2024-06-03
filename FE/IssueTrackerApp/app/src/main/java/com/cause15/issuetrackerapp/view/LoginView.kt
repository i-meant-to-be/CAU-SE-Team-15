package com.cause15.issuetrackerapp.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.cause15.issuetrackerapp.viewmodel.LoginViewModel

@Composable
fun LoginView(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    var coroutineScope = rememberCoroutineScope()

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxWidth()
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.padding(vertical = 25.dp)
        ) {
            Text(
                text = "Issue Tracker Service",
                style = MaterialTheme.typography.headlineMedium
            )
            Box(modifier = Modifier.height(10.dp))
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
            Box(modifier = Modifier.height(10.dp))
            Button(
                onClick = {
                    viewModel.login(
                        func = {
                            navHostController.navigate(
                                route = NavigationItem.IssueListViewItem.route + "/${viewModel.loginResult.value!!.id}"
                            ) {
                                popUpTo(NavigationItem.LoginViewItem.route) {
                                    inclusive = true
                                }
                            }
                        }
                    )
                }
            ) {
                Text("Login")
            }
            Box(modifier = Modifier.height(10.dp))
            if (viewModel.errorMessage.value.isNotEmpty()) {
                Text(
                    text = viewModel.errorMessage.value,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}