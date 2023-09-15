package com.example.tat.features.di

import android.content.Context
import androidx.room.Room
import com.example.tat.features.executionhistory.data.datasource.database.dao.UserRunDao
import com.example.tat.features.home.data.datasource.database.CatDatabase
import com.example.tat.features.home.data.datasource.database.dao.CatDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): CatDatabase {
        return Room.databaseBuilder(
            appContext,
            CatDatabase::class.java,
            "cat_database"
        ).createFromAsset("database/cat.db").build()
    }

    @Singleton
    @Provides
    fun provideCatDao(dataBase: CatDatabase): CatDao =
        dataBase.catDao

    @Singleton
    @Provides
    fun provideUserRunDao(dataBase: CatDatabase): UserRunDao =
        dataBase.userRunDao
}