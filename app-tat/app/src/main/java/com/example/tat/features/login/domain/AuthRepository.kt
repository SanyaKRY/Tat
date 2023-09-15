package com.example.tat.features.login.domain

import com.example.tat.features.login.data.datasource.api.model.UserAuthApi
import com.example.tat.features.login.data.datasource.api.model.LoginResponseApi
import com.example.tat.core.datatype.Result

interface AuthRepository {

    suspend fun login(userAuthApi: UserAuthApi): Result<LoginResponseApi>
}