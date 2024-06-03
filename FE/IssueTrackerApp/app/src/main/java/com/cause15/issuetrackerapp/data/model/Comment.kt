package com.cause15.issuetrackerapp.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime
import java.util.UUID

@Parcelize
data class Comment(
    @SerializedName("id") private var id: UUID,
    @SerializedName("body") private var body: String,
    @SerializedName("authorId") private var authorId: UUID,
    @SerializedName("date") private var date: String
) : Parcelable {
    constructor(body: String, authorId: UUID) : this(
        UUID.randomUUID(),
        body,
        authorId,
        ""
    )
}