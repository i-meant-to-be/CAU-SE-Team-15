package com.cause15.issuetrackerapp.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.cause15.issuetrackerapp.viewmodel.IssueDetailViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IssueDetailView(
    issueId: String,
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    viewModel: IssueDetailViewModel = hiltViewModel()
) {
    viewModel.getIssue(issueId)
    viewModel.loadUsers()

    Column(
        verticalArrangement = Arrangement.Top,
        modifier = modifier.fillMaxSize()
    ) {
        TopAppBar(
            title = { Text(text = "Issue Detail") },
            navigationIcon = { IconButton(onClick = { navHostController.popBackStack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null
                )
            } },
            actions = {
                if (viewModel.editMode.value) {
                    IconButton(onClick = {
                        viewModel.setEditMode(!viewModel.editMode.value)
                        viewModel.patchIssue()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = null
                        )
                    }
                } else {
                    IconButton(onClick = { viewModel.setEditMode(!viewModel.editMode.value) }) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = null
                        )
                    }
                }
            }
        )
        Column(
            verticalArrangement = Arrangement.Top,
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
        ) {
            IssueDetailItem(
                title = "Title",
                body = viewModel.title,
                setter = { viewModel.setTitle(it) },
                editMode = viewModel.editMode.value
            )
            IssueDetailItem(
                title = "Description",
                body = viewModel.description,
                setter = { viewModel.setDescription(it) },
                editMode = viewModel.editMode.value
            )
            IssueDetailItem(
                title = "Date",
                body = viewModel.reportedDate,
                setter = { viewModel.setTitle(it) },
                editMode = false
            )
            IssueDetailItem(
                title = "Type",
                body = viewModel.type,
                setter = { viewModel.setTitle(it) },
                editMode = false
            )
            IssueDetailItem(
                title = "State",
                body = viewModel.state,
                setter = { viewModel.setTitle(it) },
                editMode = false
            )
            IssueDetailItem(
                title = "Reporter",
                body = viewModel.reporterName,
                setter = { viewModel.setTitle(it) },
                editMode = false
            )
            IssueDetailItem(
                title = "Assignee",
                body = viewModel.assigneeName,
                setter = { viewModel.setTitle(it) },
                editMode = false
            )
            IssueDetailItem(
                title = "Fixer",
                body = viewModel.fixerName,
                setter = { viewModel.setTitle(it) },
                editMode = true,
                isLastItem = false
            )
        }
    }
}

@Composable
fun IssueDetailItem(
    title: String,
    body: State<String>,
    setter: (String) -> Unit = {},
    editMode: Boolean,
    isLastItem: Boolean = false
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
        ) {
            Column() {
                Text(
                    title,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        textAlign = TextAlign.Left
                    )
                )
                Box(modifier = Modifier.height(5.dp))
                if (!editMode) {
                    Text(
                        body.value,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Left
                        )
                    )
                } else {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = body.value,
                        onValueChange = { newValue -> setter(newValue) }
                    )
                }
            }
        }
        if (!isLastItem) HorizontalDivider()
    }
}