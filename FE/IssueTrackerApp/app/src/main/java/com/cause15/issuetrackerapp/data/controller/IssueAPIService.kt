package com.cause15.issuetrackerapp.data.controller

import com.cause15.issuetrackerapp.data.dto.PatchIssueRequest
import com.cause15.issuetrackerapp.data.model.Issue
import com.cause15.issuetrackerapp.util.enums.IssueState
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.UUID

interface IssueAPIService {
    @GET("issue/{id}")
    suspend fun getIssue(
        @Path("id") id: UUID
    ): Response<Issue>

    @GET("issue")
    suspend fun searchIssueByTitleAndState(
        @Query("title") title: String?,
        @Query("state") state: IssueState?
    ): Response<List<Issue>>

    @DELETE("issue/{id}")
    suspend fun deleteIssue(
        @Path("id") id: UUID
    ): Response<Boolean>

    @PATCH("issue/{id}")
    suspend fun patchIssue(
        @Path("id") id: UUID,
        @Body patchIssueRequest: PatchIssueRequest
    ): Response<Issue>

}