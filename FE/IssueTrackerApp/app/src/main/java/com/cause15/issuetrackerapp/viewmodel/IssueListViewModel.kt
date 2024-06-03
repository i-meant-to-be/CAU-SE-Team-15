package com.cause15.issuetrackerapp.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cause15.issuetrackerapp.data.controller.IssueAPIService
import com.cause15.issuetrackerapp.data.controller.UserAPIService
import com.cause15.issuetrackerapp.data.model.Issue
import com.cause15.issuetrackerapp.data.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class IssueListViewModel @Inject constructor(
    private val userAPIService: UserAPIService,
    private val issueAPIService: IssueAPIService
) : ViewModel() {
    private val _issueList = mutableStateOf<List<Issue>>(listOf())
    private val _loginUser = mutableStateOf(User())
    val issueList: State<List<Issue>> = _issueList
    val loginUser: State<User> = _loginUser

    fun getAllIssue() {
        viewModelScope.launch {
            val response = issueAPIService.searchIssueByTitleAndState(null, null)

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) _issueList.value = body
            }
        }
    }

    fun getUser(userId: String) {
        viewModelScope.launch {
            val response = userAPIService.getUser(UUID.fromString(userId))

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) _loginUser.value = body
            }
        }
    }
}