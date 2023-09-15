package com.example.tat.features.login.data.repository

import com.example.tat.features.login.data.datasource.api.AuthNetworkDataSource
import com.example.tat.features.login.domain.AuthRepository
import com.example.tat.features.login.data.datasource.api.model.UserAuthApi
import com.example.tat.features.login.data.datasource.api.model.LoginResponseApi
import com.example.tat.core.datatype.Result
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authNetworkDataSource: AuthNetworkDataSource
) : AuthRepository {

    override suspend fun login(userAuthApi: UserAuthApi): Result<LoginResponseApi> {
        return authNetworkDataSource.login(userAuthApi)
    }
}