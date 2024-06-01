package com.cause15.issuetrackerapp.data.model

import java.time.LocalDateTime
import java.util.UUID

class Project(
    private var id: UUID,
    private var issueIds: List<UUID>,
    private var userIds: List<UUID>,
    private var title: String,
    private var description: String,
    private var createdDate: LocalDateTime
) {
    constructor(
        issueIds: List<UUID>,
        userIds: List<UUID>,
        title: String,
        description: String
    ): this(
        UUID.randomUUID(),
        issueIds,
        userIds,
        title,
        description,
        LocalDateTime.now()
    )

    fun copy(
        id: UUID?,
        issueIds: List<UUID>?,
        userIds: List<UUID>?,
        title: String?,
        description: String?,
        createdDate: LocalDateTime?
    ): Project {
        return Project(
            id = id ?: this.id,
            issueIds = issueIds ?: this.issueIds,
            userIds = userIds ?: this.userIds,
            title = title ?: this.title,
            description = description ?: this.description,
            createdDate = createdDate ?: this.createdDate
        )
    }
}