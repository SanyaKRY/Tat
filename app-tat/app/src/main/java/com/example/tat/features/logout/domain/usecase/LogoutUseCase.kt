package com.example.tat.features.logout.domain.usecase

import com.example.tat.features.logout.domain.LogoutRepository
import javax.inject.Inject

open class LogoutUseCase @Inject constructor(
    private val repository: LogoutRepository
) {

    suspend fun execute() {
        return repository.logout()
    }
}