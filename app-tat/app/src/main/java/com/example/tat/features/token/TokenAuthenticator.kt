package com.example.tat.features.token

//import com.example.tat.features.di.TEST_EDITOR_SERVICE_PATH
import com.example.tat.features.token.api.TokenApiService
import com.example.tat.features.token.manager.TokenManager
import com.example.tat.features.token.model.RefreshTokenApi
import com.example.tat.features.token.model.TokensApi
import com.example.tat.utils.exceptions.handleNetworkExceptions
import com.example.tat.core.datatype.Result
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Request
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.Route
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.lang.Exception
import javax.inject.Inject
import com.example.tat.BuildConfig

class TokenAuthenticator @Inject constructor(
    private val tokenManager: TokenManager
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        return runBlocking {
            when (val tokenResponse = getUpdatedTokens()) {
                is Result.Success -> {
                    tokenResponse.data.let {
                        tokenManager.saveToken(tokenResponse.data.accessToken, tokenResponse.data.refreshToken)
                        response.request.newBuilder()
                            .header("Authorization", "Bearer ${it.accessToken}")
                            .build()
                    }
                }
                is Result.Error -> {
                    // TODO
                    null
                }
                is Result.Loading -> {
                    // TODO
                    null
                }
            }
        }
    }

    private suspend fun getUpdatedTokens(): Result<TokensApi> {
        return try {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            val okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.TEST_EDITOR_SERVICE_PATH)
//                .baseUrl(TEST_EDITOR_SERVICE_PATH)
                .addConverterFactory(MoshiConverterFactory.create())
                .client(okHttpClient)
                .build()
            val service = retrofit.create(TokenApiService::class.java)
            val tokensApi = service.refreshTokens(RefreshTokenApi(tokenManager.refreshToken.first()!!))
            Result.Success(tokensApi)
        } catch (ex: Exception) {
            Result.Error(handleNetworkExceptions(ex))
        }
    }
}