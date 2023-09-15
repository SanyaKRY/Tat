package com.example.tat.features.di

import com.example.tat.features.executionhistory.data.repository.UserRepositoryImpl
import com.example.tat.features.executionhistory.domain.UserRepository
import com.example.tat.features.logout.data.repository.LogoutRepositoryImpl
import com.example.tat.features.logout.domain.LogoutRepository
import com.example.tat.features.home.data.repository.HomeRepositoryImpl
import com.example.tat.features.home.domain.HomeRepository
import com.example.tat.features.login.data.repository.AuthRepositoryImpl
import com.example.tat.features.login.domain.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindHomeRepository(
        homeRepositoryImpl: HomeRepositoryImpl
    ): HomeRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindLogoutRepository(
        logoutRepositoryImpl: LogoutRepositoryImpl
    ): LogoutRepository
}