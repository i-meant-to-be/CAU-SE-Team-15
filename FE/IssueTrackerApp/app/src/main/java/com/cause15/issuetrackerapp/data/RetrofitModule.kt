package com.cause15.issuetrackerapp.data

import com.cause15.issuetrackerapp.data.controller.CommentAPIService
import com.cause15.issuetrackerapp.data.controller.IssueAPIService
import com.cause15.issuetrackerapp.data.controller.ProjectAPIService
import com.cause15.issuetrackerapp.data.controller.UserAPIService
import com.cause15.issuetrackerapp.util.Constant
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Provides
    @Singleton
    fun provideUserAPIService(): UserAPIService = Retrofit.Builder()
        .baseUrl(Constant.API.URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(UserAPIService::class.java)

    @Provides
    @Singleton
    fun provideProjectAPIService(): ProjectAPIService = Retrofit.Builder()
        .baseUrl(Constant.API.URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ProjectAPIService::class.java)

    @Provides
    @Singleton
    fun provideCommentAPIService(): CommentAPIService = Retrofit.Builder()
        .baseUrl(Constant.API.URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CommentAPIService::class.java)

    @Provides
    @Singleton
    fun provideIssueAPIService(): IssueAPIService = Retrofit.Builder()
            .baseUrl(Constant.API.URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IssueAPIService::class.java)
}