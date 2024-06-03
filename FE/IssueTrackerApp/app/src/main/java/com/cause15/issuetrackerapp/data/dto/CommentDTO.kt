package com.cause15.issuetrackerapp.data.dto

import java.util.UUID

class CreateCommentDTO(
    var body: String,
    var authorId: UUID
)

class PatchCommentDTO(
    var body: String
)