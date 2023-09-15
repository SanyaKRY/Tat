package com.example.tat.features.executionhistory.data.datasource.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.tat.features.executionhistory.data.datasource.database.model.UserRunsTableDatabase
import kotlinx.coroutines.flow.Flow

@Dao
interface UserRunDao {

    @Insert
    suspend fun insert(userRuns: UserRunsTableDatabase)

    @Query("DELETE FROM user_runs_table")
     fun deleteAll(): Int

    @Query("SELECT * FROM user_runs_table")
    fun getUserRunsFlow(): Flow<UserRunsTableDatabase>

    @Query("SELECT * FROM user_runs_table")
    suspend fun getUserRuns(): UserRunsTableDatabase
}