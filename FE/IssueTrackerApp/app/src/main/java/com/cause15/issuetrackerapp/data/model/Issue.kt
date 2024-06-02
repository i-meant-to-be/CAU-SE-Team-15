package com.cause15.issuetrackerapp.data.model

import android.os.Parcelable
import com.cause15.issuetrackerapp.util.enums.IssueState
import com.cause15.issuetrackerapp.util.enums.IssueType
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime
import java.util.UUID

@Parcelize
data class Issue(
    var id: UUID,
    var title: String,
    var description: String,
    var type: IssueType,
    var state: IssueState,
    var reportedDate: LocalDateTime,
    var commendIds: List<UUID>,
    var reporterId: UUID,
    var fixerId: UUID?,
    var assigneeId: UUID?,
    var tags: List<String>
) : Parcelable {
    constructor(
        title: String,
        description: String,
        type: IssueType,
        reporterId: UUID,
        tags: List<String>
    ): this(
        id = UUID.randomUUID(),
        title = title,
        description = description,
        type = type,
        state = IssueState.NEW,
        reportedDate = LocalDateTime.now(),
        commendIds = mutableListOf(),
        reporterId = reporterId,
        fixerId = null,
        assigneeId = null,
        tags = mutableListOf()
    )
}