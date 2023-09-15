package com.example.tat.unit.executionhistory

import com.example.tat.features.executionhistory.data.datasource.database.dao.UserRunDao
import com.example.tat.features.executionhistory.data.datasource.database.model.UserRunsTableDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class FakeUserRunDao : UserRunDao {

    private val flow = MutableSharedFlow<UserRunsTableDatabase>()
    suspend fun emit(value: UserRunsTableDatabase) = flow.emit(value)

    override suspend fun insert(userRuns: UserRunsTableDatabase) {
        // TODO
    }

    override fun deleteAll(): Int {
        // TODO
        return 0
    }

    override fun getUserRunsFlow(): Flow<UserRunsTableDatabase> {
        return flow
    }

    override suspend fun getUserRuns(): UserRunsTableDatabase {
        // TODO
        return UserRunsTableDatabase(1, emptyList())
    }
}