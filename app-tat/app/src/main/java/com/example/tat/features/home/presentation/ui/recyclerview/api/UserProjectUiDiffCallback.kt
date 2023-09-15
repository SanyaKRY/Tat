package com.example.tat.features.home.presentation.ui.recyclerview.api

import androidx.recyclerview.widget.DiffUtil
import com.example.tat.features.home.presentation.model.UserProjectUi

class UserProjectUiDiffCallback : DiffUtil.ItemCallback<UserProjectUi>() {

    override fun areItemsTheSame(oldItem: UserProjectUi, newItem: UserProjectUi): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: UserProjectUi, newItem: UserProjectUi): Boolean {
        return oldItem == newItem
    }
}