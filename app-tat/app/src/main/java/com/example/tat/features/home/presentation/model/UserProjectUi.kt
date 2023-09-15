package com.example.tat.features.home.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserProjectUi(
    val projectId: Int,
    val projectName: String
): Parcelable
