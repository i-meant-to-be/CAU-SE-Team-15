package com.cause15.issuetrackerapp.data.dto

import com.cause15.issuetrackerapp.util.enums.IssueState
import com.cause15.issuetrackerapp.util.enums.IssueType
import java.util.UUID


class PatchIssueRequest(
    val title: String? = null,
    val description: String? = null,
    val type: IssueType? = null,
    val reporterId: UUID? = null,
    val assigneeId: UUID? = null,
    val fixerId: UUID? = null,
    val state: IssueState? = null
)
