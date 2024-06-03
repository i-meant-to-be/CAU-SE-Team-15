package com.cause15.issuetrackerapp.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime
import java.util.UUID

@Parcelize
data class Project(
    @SerializedName("id") private var id: UUID,
    @SerializedName("issueIds") private var issueIds: List<UUID>,
    @SerializedName("userIds") private var userIds: List<UUID>,
    @SerializedName("title") private var title: String,
    @SerializedName("description") private var description: String,
    @SerializedName("createdDate") private var createdDate: LocalDateTime
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