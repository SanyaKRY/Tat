package com.example.tat.features.home.data.datasource.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.example.tat.features.home.data.datasource.database.model.CatTableDatabase
import kotlinx.coroutines.flow.Flow

@Dao
interface CatDao {

    @Query("SELECT * FROM cat_table")
    fun getCats(): Flow<List<CatTableDatabase>>

    @Update
    suspend fun updateCat(catTableDatabase: CatTableDatabase): Int
}