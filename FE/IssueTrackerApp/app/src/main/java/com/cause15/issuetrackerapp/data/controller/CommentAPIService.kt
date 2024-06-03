package com.cause15.issuetrackerapp.data.controller

import com.cause15.issuetrackerapp.data.dto.CreateCommentDTO
import com.cause15.issuetrackerapp.data.model.Comment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.UUID

interface CommentAPIService {
    @POST("issue/{issue_id}/comment")
    suspend fun createCommentOnIssue(
        @Path("issue_id") issueId: UUID,
        @Body createCommentDTO: CreateCommentDTO
    ): Response<Comment>

    @GET("issue/{issue_id}/comment")
    suspend fun getAllCommentsOnIssue(
        @Path("issue_id") issueId: UUID
    ): Response<List<Comment>>

    @GET("comment/{id}")
    suspend fun getComment(
        @Path("id") id: UUID
    ): Response<Comment>

    @DELETE("comment/{id}")
    suspend fun deleteComment(
        @Path("id") id: UUID
    ): Response<Boolean>
}