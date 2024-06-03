package com.cause15.issuetrackerapp.data.model

import android.os.Parcelable
import com.cause15.issuetrackerapp.util.enums.IssueState
import com.cause15.issuetrackerapp.util.enums.IssueType
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime
import java.util.UUID

@Parcelize
data class Issue(
    @SerializedName("id") var id: UUID,
    @SerializedName("title") var title: String,
    @SerializedName("description") var description: String,
    @SerializedName("type") var type: IssueType,
    @SerializedName("state") var state: IssueState,
    @SerializedName("reportedDate") var reportedDate: LocalDateTime,
    @SerializedName("commentIds") var commendIds: List<UUID>,
    @SerializedName("reporterId") var reporterId: UUID,
    @SerializedName("fixerId") var fixerId: UUID?,
    @SerializedName("assigneeId") var assigneeId: UUID?,
    @SerializedName("tags") var tags: List<String>
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
        tags = tags
    )
}