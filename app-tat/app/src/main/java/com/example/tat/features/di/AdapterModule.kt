package com.example.tat.features.di

import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.example.tat.features.home.presentation.model.UserProjectUi
import com.example.tat.features.home.presentation.ui.recyclerview.api.UserProjectUiAdapter
import dagger.assisted.AssistedFactory

@AssistedFactory
interface AdapterModule {

    fun createProjectUiAdapter(
        userProjectDetailListener: (userProjectUi: UserProjectUi, AppCompatImageView, AppCompatTextView) -> Unit
    ): UserProjectUiAdapter
}
