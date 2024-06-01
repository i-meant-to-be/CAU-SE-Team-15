package com.cause15.issuetrackerapp.data.model

import java.time.LocalDateTime
import java.util.UUID

class Comment(
    private var id: UUID,
    private var body: String,
    private var authorId: UUID,
    private var date: LocalDateTime
) {
    constructor(body: String, authorId: UUID) : this(
        UUID.randomUUID(),
        body,
        authorId,
        LocalDateTime.now()
    )

    fun copy(
        id: UUID?,
        body: String?,
        authorId: UUID?,
        date: LocalDateTime?
    ): Comment {
        return Comment(
            id = id ?: this.id,
            body = body ?: this.body,
            authorId = authorId ?: this.authorId,
            date = date ?: this.date
        )
    }
}