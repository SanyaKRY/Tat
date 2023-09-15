package com.example.tat.features.executionhistory.data.datasource.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "user_runs_table")
data class UserRunsTableDatabase(
    @PrimaryKey(autoGenerate = true)
    var id : Int,
    var listOfUserRuns : List<UserRunTableDatabase>
)


class Converter {

    @TypeConverter
    fun fromStringArrayList(listOfUserRuns : List<UserRunTableDatabase>): String {

        return Gson().toJson(listOfUserRuns)
    }

    @TypeConverter
    fun toStringArrayList(value: String): List<UserRunTableDatabase> {
        return try {
            Gson().fromJson<List<UserRunTableDatabase>>(value)
        } catch (e: Exception) {
            arrayListOf()
        }
    }
}

inline fun <reified T> Gson.fromJson(json: String) =
    fromJson<T>(json, object : TypeToken<T>() {}.type)
