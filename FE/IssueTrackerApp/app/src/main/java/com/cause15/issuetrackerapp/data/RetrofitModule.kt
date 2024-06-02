package com.cause15.issuetrackerapp.data

import com.cause15.issuetrackerapp.data.controller.ProjectAPIService
import com.cause15.issuetrackerapp.data.controller.UserAPIService
import com.cause15.issuetrackerapp.util.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
}