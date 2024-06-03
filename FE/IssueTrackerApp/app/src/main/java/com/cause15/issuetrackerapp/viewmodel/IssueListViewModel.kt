package com.cause15.issuetrackerapp.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cause15.issuetrackerapp.data.controller.IssueAPIService
import com.cause15.issuetrackerapp.data.controller.UserAPIService
import com.cause15.issuetrackerapp.data.model.Issue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IssueListViewModel @Inject constructor(
    private val userAPIService: UserAPIService,
    private val issueAPIService: IssueAPIService
) : ViewModel() {
    private val _issueList = mutableStateOf<List<Issue>?>(null)
    private val _errorCode = mutableIntStateOf(-1)
    val issueList: State<List<Issue>?> = _issueList
    val errorCode: State<Int> = _errorCode

    fun getAllIssue() {
        viewModelScope.launch {
            val response = issueAPIService.getAllIssues()

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) _issueList.value = body
            } else {
                _errorCode.intValue = response.code()
            }
        }
    }
}