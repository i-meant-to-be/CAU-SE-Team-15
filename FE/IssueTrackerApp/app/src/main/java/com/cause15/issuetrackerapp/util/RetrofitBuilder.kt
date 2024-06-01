package com.cause15.issuetrackerapp.util

import com.cause15.issuetrackerapp.data.controller.ProjectController
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    var projectController: ProjectController = Retrofit.Builder()
        .baseUrl(Constant.API.URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ProjectController::class.java)
}