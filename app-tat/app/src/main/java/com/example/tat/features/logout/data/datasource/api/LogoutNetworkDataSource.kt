package com.example.tat.features.logout.data.datasource.api

import com.example.tat.features.logout.data.datasource.api.retrofit.LogoutApiService
import com.example.tat.core.datatype.Result
import java.lang.Exception
import javax.inject.Inject

class LogoutNetworkDataSource @Inject constructor(
    private val logoutApiService: LogoutApiService
) {

    suspend fun logout() {
        try {
            logoutApiService.logout()
        } catch (ex: Exception) {
            Result.Error(ex)
        }
    }
}