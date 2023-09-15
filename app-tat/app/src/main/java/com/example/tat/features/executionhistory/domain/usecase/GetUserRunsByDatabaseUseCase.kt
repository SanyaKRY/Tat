package com.example.tat.features.executionhistory.domain.usecase

import com.example.tat.core.datatype.Result
import com.example.tat.features.executionhistory.domain.UserRepository
import com.example.tat.features.executionhistory.domain.model.UserRunDomain
import com.example.tat.features.executionhistory.domain.model.UserRunsDomain
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserRunsByDatabaseUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    fun execute(): Flow<Result<UserRunsDomain>> {
        return userRepository.getUserRunsByDatabase()
    }
}