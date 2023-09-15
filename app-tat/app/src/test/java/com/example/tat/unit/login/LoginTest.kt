package com.example.tat.unit.login

import com.google.common.truth.Truth.assertThat
import com.example.tat.core.datatype.Result
import com.example.tat.features.login.data.datasource.api.AuthNetworkDataSource
import com.example.tat.features.login.data.datasource.api.model.LoginResponseApi
import com.example.tat.features.login.data.datasource.api.model.UserAuthApi
import com.example.tat.features.login.data.datasource.api.retrofit.AuthApiService
import com.example.tat.features.login.data.repository.AuthRepositoryImpl
import com.example.tat.features.login.domain.AuthRepository
import com.example.tat.features.login.domain.usecase.LoginUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class LoginTest {

    private lateinit var login: LoginUseCase
    private lateinit var authRepository: AuthRepository
    private lateinit var authNetworkDataSource: AuthNetworkDataSource
    private lateinit var fakeAuthApiService: AuthApiService

    @Before
    fun setUp() {
        fakeAuthApiService = FakeAuthApiService()
        authNetworkDataSource = AuthNetworkDataSource(fakeAuthApiService)
        authRepository = AuthRepositoryImpl(authNetworkDataSource)
        login = LoginUseCase(authRepository)
    }

    @Test
    fun `Log in, positive test`() = runBlocking {
        val userAuth: UserAuthApi = UserAuthApi("admin", "password")
        val result: Result<LoginResponseApi> = login.execute(userAuth)
        assertThat(result).isNotNull()

        result as Result.Success<LoginResponseApi>
        assertThat(result.data.accessToken).isNotNull()
        assertThat(result.data.refreshToken).isNotNull()
        assertThat(result.data.userId).isNotNull()
        assertThat(result.data.username).isNotNull()
        assertThat(result.data.username).isEqualTo("usernameTest")
    }
}