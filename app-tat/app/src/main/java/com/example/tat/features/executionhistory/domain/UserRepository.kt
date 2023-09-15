package com.example.tat.features.executionhistory.domain

import com.example.tat.core.datatype.Result
import com.example.tat.features.executionhistory.domain.model.UserRunsDomain
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getUserRunsByDatabase(): Flow<Result<UserRunsDomain>>
}