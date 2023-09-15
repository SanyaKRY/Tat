package com.example.tat.features.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.tat.BuildConfig
import com.example.tat.features.logout.data.datasource.api.retrofit.LogoutApiService
import com.example.tat.features.home.data.datasource.api.retrofit.UserApiService
import com.example.tat.features.login.data.datasource.api.retrofit.AuthApiService
import com.example.tat.features.token.AuthInterceptor
import com.example.tat.features.token.TokenAuthenticator
import com.example.tat.features.token.api.TokenApiService
import com.example.tat.features.token.manager.TokenManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "data_store")

//const val TEST_EDITOR_SERVICE_PATH = "http://10.68.84.25:8080"
//const val TEST_EXECUTION_SERVICE_PATH = "http://10.68.84.25:8090"

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideTokenManager(@ApplicationContext context: Context): TokenManager = TokenManager(context)

    @Singleton
    @Provides
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor,
        tokenAuthenticator: TokenAuthenticator,
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .authenticator(tokenAuthenticator)
            .build()
    }

    @Singleton
    @Provides
    fun provideAuthInterceptor(tokenManager: TokenManager): AuthInterceptor =
        AuthInterceptor(tokenManager)

    @Singleton
    @Provides
    fun provideTokenAuthenticator(tokenManager: TokenManager): TokenAuthenticator =
        TokenAuthenticator(tokenManager)

    @Singleton
    @Provides
    fun provideRetrofitBuilder(): Retrofit.Builder =
        Retrofit.Builder()
            .baseUrl(BuildConfig.TEST_EDITOR_SERVICE_PATH)
//            .baseUrl(TEST_EDITOR_SERVICE_PATH)
            .addConverterFactory(MoshiConverterFactory.create())

    @Singleton
    @Provides
    fun provideAuthApiService(retrofit: Retrofit.Builder): AuthApiService =
        retrofit
            .build()
            .create(AuthApiService::class.java)

    @Singleton
    @Provides
    fun provideUserApiService(okHttpClient: OkHttpClient, retrofit: Retrofit.Builder): UserApiService =
        retrofit
            .client(okHttpClient)
            .build()
            .create(UserApiService::class.java)

    @Singleton
    @Provides
    fun provideTokenApiService(okHttpClient: OkHttpClient, retrofit: Retrofit.Builder): TokenApiService =
        retrofit
            .client(okHttpClient)
            .build()
            .create(TokenApiService::class.java)

    @Singleton
    @Provides
    fun provideLogoutApiService(okHttpClient: OkHttpClient, retrofit: Retrofit.Builder): LogoutApiService =
        retrofit
            .client(okHttpClient)
            .build()
            .create(LogoutApiService::class.java)
}