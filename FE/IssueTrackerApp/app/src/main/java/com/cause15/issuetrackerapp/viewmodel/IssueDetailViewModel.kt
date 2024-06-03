package com.cause15.issuetrackerapp.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cause15.issuetrackerapp.data.controller.IssueAPIService
import com.cause15.issuetrackerapp.data.controller.UserAPIService
import com.cause15.issuetrackerapp.data.dto.PatchIssueRequest
import com.cause15.issuetrackerapp.data.model.Issue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class IssueDetailViewModel @Inject constructor(
    private val issueAPIService: IssueAPIService,
    private val userAPIService: UserAPIService
) : ViewModel() {
    private val _issue = mutableStateOf(Issue())
    private val _reporterName = mutableStateOf("(Not assigned)")
    private val _fixerName = mutableStateOf("(Not assigned)")
    private val _assigneeName = mutableStateOf("(Not assigned)")
    private val _editMode = mutableStateOf(false)
    val issue: State<Issue> = _issue
    val reporterName: State<String> = _reporterName
    val fixerName: State<String> = _fixerName
    val assigneeName: State<String> = _assigneeName
    val editMode: State<Boolean> = _editMode

    private val _title = mutableStateOf("")
    private val _description = mutableStateOf("")
    private val _type = mutableStateOf("")
    private val _state = mutableStateOf("")
    private val _reportedDate = mutableStateOf("")
    private val _commendIds = mutableStateOf<List<UUID>>(listOf())
    val title: State<String> = _title
    val description: State<String> = _description
    val type: State<String> = _type
    val state: State<String> = _state
    val reportedDate: State<String> = _reportedDate
    val commendIds: State<List<UUID>> = _commendIds

    fun setTitle(title: String) {
        _title.value = title
    }
    fun setDescription(description: String) {
        _description.value = description
    }
    fun setType(type: String) {
        _type.value = type
    }
    fun setState(state: String) {
        _state.value = state
    }
    fun setReportedDate(reportedDate: String) {
        _reportedDate.value = reportedDate
    }


    fun setEditMode(editMode: Boolean) {
        _editMode.value = editMode
    }

    fun patchIssue() {
        val patchIssueRequest = PatchIssueRequest(
            title = _title.value,
            description = _description.value,
        )
        viewModelScope.launch {
            val response = issueAPIService.patchIssue(UUID.fromString(_issue.value.id.toString()), patchIssueRequest)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    _issue.value = body
                    _title.value = body.title
                    _description.value = body.description
                }
            }
        }
    }

    fun getIssue(issueId: String) {
        viewModelScope.launch {
            val response = issueAPIService.getIssue(UUID.fromString(issueId))

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    _issue.value = body
                    _title.value = body.title
                    _description.value = body.description
                    _type.value = body.type.toString()
                    _state.value = body.state.toString()
                    _reportedDate.value = body.reportedDate
                    _commendIds.value = body.commentIds
                }
            }
        }
    }

    fun loadUsers() {
        viewModelScope.launch {
            val response1 = userAPIService.getUser(_issue.value.reporterId)
            if (response1.isSuccessful) {
                if (response1.body() != null) _reporterName.value = response1.body()!!.name
            }

            if (_issue.value.assigneeId != null) {
                val response2 = userAPIService.getUser(_issue.value.assigneeId!!)
                if (response2.isSuccessful) {
                    if (response2.body() != null) _assigneeName.value = response2.body()!!.name
                }
            }

            if (_issue.value.fixerId != null) {
                val response3 = userAPIService.getUser(_issue.value.fixerId!!)
                if (response3.isSuccessful) {
                    if (response3.body() != null) _fixerName.value = response3.body()!!.name
                }
            }
        }
    }
}