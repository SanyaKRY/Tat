package com.example.tat.features.home.data.datasource.api

import com.example.tat.features.home.data.datasource.api.model.UserApi
import com.example.tat.features.home.data.datasource.api.model.UserProjectsApi
import com.example.tat.features.home.data.datasource.api.retrofit.UserApiService
import com.example.tat.utils.exceptions.handleNetworkExceptions
import com.example.tat.core.datatype.Result
import java.lang.Exception
import javax.inject.Inject

const val SKIP_PAGEABLE = true

class UserNetworkDataSource @Inject constructor(
    private val userApiService: UserApiService
) {

    suspend fun getInformationAboutMe(): Result<UserApi> {
        return try {
            val userApi = userApiService.getInformationAboutMe()
            Result.Success(userApi)
        } catch (ex: Exception) {
            Result.Error(handleNetworkExceptions(ex))
        }
    }

    suspend fun getMyProjects(): Result<UserProjectsApi> {
        return try {
            val myProjects = userApiService.getMyProjects("id,desc", SKIP_PAGEABLE)
            Result.Success(myProjects)
        } catch (ex: Exception) {
            Result.Error(handleNetworkExceptions(ex))
        }
    }
}