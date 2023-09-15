package com.example.tat.features.home.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CatUi(
    var id : Int,
    val age: Int,
    val name: String
): Parcelable
