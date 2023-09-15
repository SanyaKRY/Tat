package com.example.tat.unit.login

import com.example.tat.features.login.data.datasource.api.model.LoginResponseApi
import com.example.tat.features.login.data.datasource.api.model.UserAuthApi
import com.example.tat.features.login.data.datasource.api.retrofit.AuthApiService

class FakeAuthApiService : AuthApiService {

    override suspend fun login(userAuthApi: UserAuthApi): LoginResponseApi {
        return LoginResponseApi("accessTokenTest", "refreshTokenTest", "usernameTest", 1)
    }
}