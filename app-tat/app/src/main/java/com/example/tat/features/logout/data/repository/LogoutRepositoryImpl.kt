package com.example.tat.features.logout.data.repository

import com.example.tat.features.logout.data.datasource.api.LogoutNetworkDataSource
import com.example.tat.features.logout.domain.LogoutRepository
import javax.inject.Inject

class LogoutRepositoryImpl @Inject constructor(
    private val logoutNetworkDataSource: LogoutNetworkDataSource
) : LogoutRepository {

    override suspend fun logout() {
        logoutNetworkDataSource.logout()
    }
}