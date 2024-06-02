package com.cause15.issuetrackerapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime
import java.util.UUID

@Parcelize
data class Comment(
    private var id: UUID,
    private var body: String,
    private var authorId: UUID,
    private var date: LocalDateTime
) : Parcelable {
    constructor(body: String, authorId: UUID) : this(
        UUID.randomUUID(),
        body,
        authorId,
        LocalDateTime.now()
    )
}