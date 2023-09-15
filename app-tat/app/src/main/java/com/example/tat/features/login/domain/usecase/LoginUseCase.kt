package com.example.tat.features.login.domain.usecase

import com.example.tat.features.login.domain.AuthRepository
import com.example.tat.features.login.data.datasource.api.model.UserAuthApi
import com.example.tat.features.login.data.datasource.api.model.LoginResponseApi
import com.example.tat.core.datatype.Result
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {

    suspend fun execute(userAuthApi: UserAuthApi): Result<LoginResponseApi> {
        return repository.login(userAuthApi)
    }
}