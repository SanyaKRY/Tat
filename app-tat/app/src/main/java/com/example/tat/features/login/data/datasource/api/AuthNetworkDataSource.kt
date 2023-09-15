package com.example.tat.features.login.data.datasource.api

import com.example.tat.features.login.data.datasource.api.model.LoginResponseApi
import com.example.tat.features.login.data.datasource.api.retrofit.AuthApiService
import com.example.tat.features.login.data.datasource.api.model.UserAuthApi
import com.example.tat.utils.exceptions.handleNetworkExceptions
import com.example.tat.core.datatype.Result
import java.lang.Exception
import javax.inject.Inject

class AuthNetworkDataSource @Inject constructor(
    private val authApiService: AuthApiService
) {

    suspend fun login(userAuthApi: UserAuthApi): Result<LoginResponseApi> {
        return try {
            val loginResponseApi = authApiService.login(userAuthApi)
            Result.Success(loginResponseApi)
        } catch (ex: Exception) {
            Result.Error(handleNetworkExceptions(ex))
        }
    }
}