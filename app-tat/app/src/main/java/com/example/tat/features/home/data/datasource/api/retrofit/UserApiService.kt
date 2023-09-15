package com.example.tat.features.home.data.datasource.api.retrofit

import com.example.tat.features.home.data.datasource.api.model.UserApi
import com.example.tat.features.home.data.datasource.api.model.UserProjectsApi
import com.example.tat.features.home.data.datasource.api.model.UserRunsApi
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface UserApiService {

    @GET("api/v1/user/me")
    suspend fun getInformationAboutMe(): UserApi

    @GET("api/v1/project")
    suspend fun getMyProjects(
        @Query("sort") sort: String,
        @Query("skipPageable") skipPageable: Boolean
    ): UserProjectsApi

    @GET
    suspend fun getMyRuns(
        @Url url: String,
        @Query("sort") sort: String,
        @Query("skipPageable") skipPageable: Boolean
    ): UserRunsApi
}