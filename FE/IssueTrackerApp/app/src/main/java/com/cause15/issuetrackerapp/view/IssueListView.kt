package com.cause15.issuetrackerapp.view

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.cause15.issuetrackerapp.data.model.Issue
import com.cause15.issuetrackerapp.viewmodel.IssueListViewModel

@Composable
fun IssueListView(
    viewModel: IssueListViewModel = hiltViewModel()
) {

}

@Composable
fun IssueListItem(issue: Issue) {

}