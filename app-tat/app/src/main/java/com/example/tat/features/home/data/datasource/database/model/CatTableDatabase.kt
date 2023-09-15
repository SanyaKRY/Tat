package com.example.tat.features.home.data.datasource.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cat_table")
class CatTableDatabase(
    @PrimaryKey(autoGenerate = true)
    var id : Int,
    val age: Int,
    val name: String
)