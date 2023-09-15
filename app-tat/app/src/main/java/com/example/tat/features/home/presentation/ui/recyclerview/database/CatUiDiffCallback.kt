package com.example.tat.features.home.presentation.ui.recyclerview.database

import androidx.recyclerview.widget.DiffUtil
import com.example.tat.features.home.presentation.model.CatUi

class CatUiDiffCallback : DiffUtil.ItemCallback<CatUi>() {

    override fun areItemsTheSame(oldItem: CatUi, newItem: CatUi): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: CatUi, newItem: CatUi): Boolean {
        return oldItem == newItem
    }
}