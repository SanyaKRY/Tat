package com.example.tat.features.executionhistory.presentation.ui.recyclerview

import androidx.recyclerview.widget.RecyclerView
import com.example.tat.R
import com.example.tat.databinding.UserRunItemBinding
import com.example.tat.features.executionhistory.presentation.model.StatusColorType
import com.example.tat.features.executionhistory.presentation.model.UserRunUi
import com.example.tat.utils.extensions.applyColor

class ViewHolderUserRunUi(private val binding: UserRunItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(userProject: UserRunUi) {
        binding.projectName.text = userProject.projectName
        binding.entityName.text = userProject.entityName
        binding.status.text = userProject.status
        binding.status.applyColor(getColor(userProject.statusColor))
    }

    private fun getColor(colorType: StatusColorType): Int {
        return when (colorType) {
            StatusColorType.GREEN -> R.color.green
            StatusColorType.ORANGE -> R.color.orange
            StatusColorType.RED -> R.color.red
            StatusColorType.GREY -> R.color.gray
        }
    }
}