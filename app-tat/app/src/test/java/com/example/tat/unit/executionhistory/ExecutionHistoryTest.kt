package com.example.tat.unit.executionhistory

import app.cash.turbine.test
import com.example.tat.core.datatype.Result
import com.example.tat.features.executionhistory.data.datasource.database.UserDatabaseDataSource
import com.example.tat.features.executionhistory.data.datasource.database.dao.UserRunDao
import com.example.tat.features.executionhistory.data.datasource.database.model.UserRunTableDatabase
import com.example.tat.features.executionhistory.data.datasource.database.model.UserRunsTableDatabase
import com.example.tat.features.executionhistory.data.repository.UserRepositoryImpl
import com.example.tat.features.executionhistory.domain.UserRepository
import com.example.tat.features.executionhistory.domain.model.UserRunsDomain
import com.example.tat.features.executionhistory.domain.usecase.GetUserRunsByDatabaseUseCase
import com.google.common.truth.Truth
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class ExecutionHistoryTest {

    private lateinit var getUserRunsByDatabaseUseCase: GetUserRunsByDatabaseUseCase
    private lateinit var userRepository: UserRepository
    private lateinit var userDatabaseDataSource: UserDatabaseDataSource
    private lateinit var fakeUserRunDao: UserRunDao


    @Before
    fun setUp() {
        fakeUserRunDao = FakeUserRunDao()
        userDatabaseDataSource = UserDatabaseDataSource(fakeUserRunDao)
        userRepository = UserRepositoryImpl(userDatabaseDataSource)
        getUserRunsByDatabaseUseCase = GetUserRunsByDatabaseUseCase(userRepository)
    }

    @Test
    fun `Get user runs flow, positive test`() = runTest {

        getUserRunsByDatabaseUseCase.execute().test {

            (fakeUserRunDao as FakeUserRunDao).emit(
                UserRunsTableDatabase(1, emptyList()))
            val first: Result<UserRunsDomain> = awaitItem()
            first as Result.Success<UserRunsDomain>
            Truth.assertThat(first.data.listOfUserRuns.size).isEqualTo(0)

            (fakeUserRunDao as FakeUserRunDao).emit(
                UserRunsTableDatabase(1, listOf(UserRunTableDatabase(2, "1", "1", 1, "1"))))
            val second: Result<UserRunsDomain> = awaitItem()
            second as Result.Success<UserRunsDomain>
            Truth.assertThat(second.data.listOfUserRuns.size).isEqualTo(1)

            cancelAndConsumeRemainingEvents()
        }
    }
}