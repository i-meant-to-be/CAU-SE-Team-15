package com.cause15.issuetrackerapp.data.controller

import com.cause15.issuetrackerapp.data.dto.LoginDTO
import com.cause15.issuetrackerapp.data.dto.CreateUserDTO
import com.cause15.issuetrackerapp.data.dto.PatchUserDTO
import com.cause15.issuetrackerapp.data.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.UUID

interface UserAPIService {
    @POST("user/login")
    suspend fun login(
        @Body loginDTO: LoginDTO
    ): Response<User>

    @POST("user")
    suspend fun createUser(
        @Body createUserDTO: CreateUserDTO
    ): Response<User>

    @DELETE("user/{id}")
    suspend fun deleteUser(
        @Path("id") id: UUID
    ): Response<Boolean>

    @GET("user/{id}")
    suspend fun getUser(
        @Path("id") id: UUID
    ): Response<User>

    @PATCH("user/{id}")
    suspend fun patchUser(
        @Path("id") id: UUID,
        @Body patchUserDTO: PatchUserDTO
    ): Response<User>
}