package com.cause15.issuetrackerapp.data.model

import com.cause15.issuetrackerapp.util.enums.IssueState
import com.cause15.issuetrackerapp.util.enums.IssueType
import java.time.LocalDateTime
import java.util.UUID

class Issue(
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
) {
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

    fun copy(
        id: UUID?,
        title: String?,
        description: String?,
        type: IssueType?,
        state: IssueState?,
        reportedDate: LocalDateTime?,
        commendIds: List<UUID>?,
        reporterId: UUID?,
        fixerId: UUID?,
        assigneeId: UUID?,
        tags: List<String>?
    ): Issue {
        return Issue(
            id = id ?: this.id,
            title = title ?: this.title,
            description = description ?: this.description,
            type = type ?: this.type,
            state = state ?: this.state,
            reportedDate = reportedDate ?: this.reportedDate,
            commendIds = commendIds ?: this.commendIds,
            reporterId = reporterId ?: this.reporterId,
            fixerId = fixerId ?: this.fixerId,
            assigneeId = assigneeId ?: this.assigneeId,
            tags = tags ?: this.tags
        )
    }
}