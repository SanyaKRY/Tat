package com.example.tat.features.executionhistory.presentation.ui.recyclerview

import androidx.recyclerview.widget.DiffUtil
import com.example.tat.features.executionhistory.presentation.model.UserRunUi

class UserRunUiDiffCallback : DiffUtil.ItemCallback<UserRunUi>() {

    override fun areItemsTheSame(oldItem: UserRunUi, newItem: UserRunUi): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: UserRunUi, newItem: UserRunUi): Boolean {
        return oldItem == newItem
    }
}