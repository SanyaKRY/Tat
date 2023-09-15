package com.example.tat.features.home.data.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.tat.features.executionhistory.data.datasource.database.dao.UserRunDao
import com.example.tat.features.executionhistory.data.datasource.database.model.Converter
import com.example.tat.features.executionhistory.data.datasource.database.model.UserRunsTableDatabase
import com.example.tat.features.home.data.datasource.database.dao.CatDao
import com.example.tat.features.home.data.datasource.database.model.CatTableDatabase

@Database(entities = arrayOf(CatTableDatabase::class, UserRunsTableDatabase::class), version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class CatDatabase: RoomDatabase() {

    abstract val catDao: CatDao

    abstract val userRunDao: UserRunDao
}