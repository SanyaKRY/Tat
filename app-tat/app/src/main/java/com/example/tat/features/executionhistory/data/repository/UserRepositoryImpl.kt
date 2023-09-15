package com.example.tat.features.executionhistory.data.repository

import com.example.tat.core.datatype.Result
import com.example.tat.features.executionhistory.data.datasource.database.UserDatabaseDataSource
import com.example.tat.features.executionhistory.data.repository.mapper.UserRunsDatabaseToDomainMapper
import com.example.tat.features.executionhistory.domain.UserRepository
import com.example.tat.features.executionhistory.domain.model.UserRunsDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDatabaseDataSource: UserDatabaseDataSource
) : UserRepository {

    override fun getUserRunsByDatabase(): Flow<Result<UserRunsDomain>> {
        return userDatabaseDataSource.getyUserRuns().map { result ->
            when (result) {
                is Result.Success -> {
                    Result.Success(UserRunsDatabaseToDomainMapper.map(result.data))
                }
                is Result.Error -> Result.Error(result.error)
                is Result.Loading -> Result.Loading
            }
        }
    }
}