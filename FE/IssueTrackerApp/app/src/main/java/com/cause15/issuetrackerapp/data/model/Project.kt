package com.cause15.issuetrackerapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime
import java.util.UUID

@Parcelize
data class Project(
    private var id: UUID,
    private var issueIds: List<UUID>,
    private var userIds: List<UUID>,
    private var title: String,
    private var description: String,
    private var createdDate: LocalDateTime
) : Parcelable {
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
}