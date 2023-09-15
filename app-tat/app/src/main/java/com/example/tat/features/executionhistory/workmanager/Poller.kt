package com.example.tat.features.executionhistory.workmanager

import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
//import com.example.tat.features.di.TEST_EXECUTION_SERVICE_PATH
import com.example.tat.features.executionhistory.data.datasource.database.dao.UserRunDao
import com.example.tat.features.executionhistory.data.datasource.database.mapper.UserRunsApiToDatabaseMapper
import com.example.tat.features.executionhistory.data.datasource.database.model.UserRunTableDatabase
import com.example.tat.features.executionhistory.data.datasource.database.model.UserRunsTableDatabase
import com.example.tat.features.home.data.datasource.api.model.UserRunsApi
import com.example.tat.features.home.data.datasource.api.retrofit.UserApiService
import com.example.tat.utils.exceptions.PROGRESS
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import com.example.tat.BuildConfig

@HiltWorker
class Poller @AssistedInject constructor(
    @Assisted private val userApiService: UserApiService,
    @Assisted private val userRunDao: UserRunDao,
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val notificationBuilder: NotificationCompat.Builder,
    private val notificationManager: NotificationManagerCompat
) : CoroutineWorker(context, workerParams) {

    companion object {
        const val UNIQUE_WORK_NAME = "UniqueWorkName"
        const val RUNNING = "RUNNING"
        const val QUEUED = "QUEUED"
        const val PASSED = "PASSED"
        const val FAILED = "FAILED"
        const val ERROR = "ERROR"
    }

    override suspend fun doWork(): Result {
        return runCatching {
            userApiService.getMyRuns(
//                "$TEST_EXECUTION_SERVICE_PATH/api/v1/user/me/runs",
                "${BuildConfig.TEST_EXECUTION_SERVICE_PATH}/api/v1/user/me/runs",
                "startTime,desc",
                true
            )
        }.fold(
            onSuccess = { result ->

                // из апи в датубазу
                val listOfRunsFromApiToDatabaseMapper: UserRunsTableDatabase = UserRunsApiToDatabaseMapper.map(result)

                // из даты базы
                val listOfRunsFromDatabase: UserRunsTableDatabase = userRunDao.getUserRuns()
//                Log.d("PollerLog", " database size: ${listOfRunsFromDatabase.listOfUserRuns.size}")

                val listOfRunsIntersectedByIdAndChangedFinalStatus =
                    getListOfRunsIntersectedByIdAndChangedFinalStatus(listOfRunsFromApiToDatabaseMapper, listOfRunsFromDatabase)

                if (listOfRunsIntersectedByIdAndChangedFinalStatus.size > 0) {
                    // run Id and status show
                    val list = listOfRunsIntersectedByIdAndChangedFinalStatus.map { it.runId }
                    notificationManager
                        .notify(1, notificationBuilder
                        .setContentText("Test ${list.joinToString(", ")} execution completed")
                        .build())
                }
                Log.d("Poller", "${listOfRunsIntersectedByIdAndChangedFinalStatus}")

//                if (runAttemptCount > 17) {
//                    return Result.failure()
//                }

                val resultDeleteAll = userRunDao.deleteAll()

                userRunDao.insert(listOfRunsFromApiToDatabaseMapper)

                val numberOfRunningTests = getNumberOfRunningTests(result)

                if (numberOfRunningTests > 0) {
                    val numberOfTestsRun = workDataOf(PROGRESS to numberOfRunningTests)
                    setProgress(numberOfTestsRun)
                    Result.retry()
                } else {
                    Result.success()
                }
            },
            onFailure = {
                Log.d("PollerLog", "onFailure $it and ${it.message}")
                Result.failure()
            }
        )
    }

    private fun getNumberOfRunningTests(result: UserRunsApi): Int {
        return result.runs.count {
            it.status.equals(RUNNING, ignoreCase = true) || it.status.equals(QUEUED, ignoreCase = true)
        }
    }

    private fun getListOfRunsIntersectedByIdAndChangedFinalStatus(
        listOfRunsFromApi: UserRunsTableDatabase,
        listOfRunsFromDatabase: UserRunsTableDatabase
    ): List<UserRunTableDatabase> {

        val result: MutableList<UserRunTableDatabase> = mutableListOf()

        if (listOfRunsFromDatabase == null) {
            return emptyList()
        }

        // пересечения по id, database
        val resultIntersectBuId: List<UserRunTableDatabase> =
            listOfRunsFromDatabase.listOfUserRuns.filter { listOfRunsApi ->
                listOfRunsFromApi.listOfUserRuns.map { listOfRunsDatabase -> listOfRunsDatabase.runId }
                    .contains(listOfRunsApi.runId)
            }

        listOfRunsFromApi.listOfUserRuns.forEach { listOfUserRunsApi ->
            // в апи имеет финальный статус
            if (listOfUserRunsApi.status.equals(PASSED) ||
                listOfUserRunsApi.status.equals(FAILED) || listOfUserRunsApi.status.equals(ERROR)) {
                val listOfUserRunsDatabase = resultIntersectBuId.firstOrNull { it.runId == listOfUserRunsApi.runId }
                if (listOfUserRunsDatabase !=null) {
                    if (listOfUserRunsDatabase.status.equals(QUEUED) || listOfUserRunsDatabase.status.equals(RUNNING)) {
                        result.add(listOfUserRunsDatabase)
                    }
                }
            }
        }
        return result
    }
}