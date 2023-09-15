package com.example.tat.features.executionhistory.presentation.ui.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.tat.databinding.UserRunItemBinding
import com.example.tat.features.executionhistory.presentation.model.UserRunUi
import javax.inject.Inject

class UserRunUiAdapter @Inject constructor() : ListAdapter<UserRunUi, ViewHolderUserRunUi>(
    UserRunUiDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderUserRunUi {
        val itemViewHolder = UserRunItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)

        val viewHolder = ViewHolderUserRunUi(itemViewHolder)

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolderUserRunUi, position: Int) {
        holder.apply {
            val current: UserRunUi = getItem(position)
            bind(current)
        }
    }
}