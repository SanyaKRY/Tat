package com.example.tat.features.executionhistory.data.datasource.database

import com.example.tat.core.datatype.Result
import com.example.tat.features.executionhistory.data.datasource.database.dao.UserRunDao
import com.example.tat.features.executionhistory.data.datasource.database.model.UserRunsTableDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserDatabaseDataSource @Inject constructor(private val userRunDao: UserRunDao)  {

    fun getyUserRuns(): Flow<Result<UserRunsTableDatabase>> {
        return userRunDao.getUserRunsFlow().filterNotNull().map {
            Result.Success(it) as Result<UserRunsTableDatabase>
        }.catch {
            emit(Result.Error(Exception(it)))
        }
    }
}