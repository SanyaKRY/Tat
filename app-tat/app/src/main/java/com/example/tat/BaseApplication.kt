package com.example.tat

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Configuration
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.tat.features.executionhistory.data.datasource.database.dao.UserRunDao
import com.example.tat.features.home.data.datasource.api.retrofit.UserApiService
import com.example.tat.features.executionhistory.workmanager.Poller
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class BaseApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: CustomWorkerFactory

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setMinimumLoggingLevel(Log.DEBUG)
            .setWorkerFactory(workerFactory)
            .build()
    }
}

class CustomWorkerFactory @Inject constructor(
    private val userApiService: UserApiService,
    private val userRunDao: UserRunDao,
    private val notificationBuilder: NotificationCompat.Builder,
    private val notificationManager: NotificationManagerCompat
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker = Poller(userApiService, userRunDao, appContext, workerParameters, notificationBuilder, notificationManager)
}