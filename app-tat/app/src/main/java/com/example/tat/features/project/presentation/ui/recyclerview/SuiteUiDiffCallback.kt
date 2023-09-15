package com.example.tat.features.project.presentation.ui.recyclerview

import androidx.recyclerview.widget.DiffUtil
import com.example.tat.features.project.presentation.model.SuiteOfProject

class SuiteUiDiffCallback : DiffUtil.ItemCallback<SuiteOfProject>() {

    override fun areItemsTheSame(oldItem: SuiteOfProject, newItem: SuiteOfProject): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: SuiteOfProject, newItem: SuiteOfProject): Boolean {
        return oldItem == newItem
    }
}