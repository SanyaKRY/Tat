package com.example.tat.features.home.data.datasource.database

import android.util.Log
import com.example.tat.core.datatype.Result
import com.example.tat.features.home.data.datasource.database.dao.CatDao
import com.example.tat.features.home.data.datasource.database.model.CatTableDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CatDatabaseDataSource @Inject constructor(private val catDao: CatDao) {

    fun getCats(): Flow<Result<List<CatTableDatabase>>> {
        return catDao.getCats().map {
            Result.Success(it) as Result<List<CatTableDatabase>>
        }.catch {
            emit(Result.Error(Exception(it)))
        }
    }

    suspend fun updateCat(catTableDatabase: CatTableDatabase): Result<Unit> {
        return try {
            val result = catDao.updateCat(catTableDatabase)
            Log.d("CatDatabaseDataSource", "result update: $result")
            Result.Success(Unit)
        } catch (ex: Exception) {
            Result.Error(Exception(ex))
        }
    }
}