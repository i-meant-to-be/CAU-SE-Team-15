package com.cause15.issuetrackerapp.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.cause15.issuetrackerapp.data.model.Issue
import com.cause15.issuetrackerapp.data.model.User
import com.cause15.issuetrackerapp.viewmodel.IssueListViewModel
import java.util.UUID

@Composable
fun IssueListView(
    userId: String,
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    viewModel: IssueListViewModel = hiltViewModel()
) {
    viewModel.getUser(userId)
    viewModel.getAllIssue()

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Text(
            text = "Welcome, ${viewModel.loginUser.value?.name}!\nHere are your issues:",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Left,
            modifier = Modifier.padding(top = 20.dp, start = 20.dp, end = 20.dp)
        )
        HorizontalDivider(
            modifier = Modifier.padding(20.dp)
        )
        if (viewModel.issueList.value.isEmpty()) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = null,
                    modifier = Modifier.size(50.dp)
                )
                Text(
                    text = "No issues found.",
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                itemsIndexed(viewModel.issueList.value) { _, issue ->
                    IssueListItem(
                        issue = issue,
                        onItemClick = {
                            navHostController.navigate(route = NavigationItem.IssueDetailViewItem.route + "/${issue.id}")
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun IssueListItem(
    issue: Issue,
    onItemClick: (Issue) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable { onItemClick(issue) }
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(
                text = issue.title,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = issue.description,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = issue.reportedDate.replace("T", " "),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}