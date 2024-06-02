package com.cause15.issuetrackerapp.data.controller

import com.cause15.issuetrackerapp.data.LoginDTO
import com.cause15.issuetrackerapp.data.model.User
import com.cause15.issuetrackerapp.util.Constant
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserAPIService {
    @POST("user/login")
    suspend fun login(
        @Body loginDTO: LoginDTO
    ): Response<User>
}