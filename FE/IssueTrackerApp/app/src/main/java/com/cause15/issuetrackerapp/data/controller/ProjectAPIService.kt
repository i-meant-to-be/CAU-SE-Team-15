package com.cause15.issuetrackerapp.data.controller

import com.cause15.issuetrackerapp.data.model.Project
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.UUID

interface ProjectAPIService {
    @GET("project")
    suspend fun getAllProjects(): Response<List<Project>>

    @GET("user/{id}/included_project")
    suspend fun getProjectsUserIncluded(@Path("id") id: UUID): Response<Project>
}