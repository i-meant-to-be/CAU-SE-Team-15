package com.cause15.issuetrackerapp.data.controller

import com.cause15.issuetrackerapp.data.model.Project
import retrofit2.Response
import retrofit2.http.GET

interface ProjectController {
    @GET("/api/project")
    suspend fun getAllProjects(): Response<List<Project>>
}