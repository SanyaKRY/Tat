package com.example.tat.features.project.presentation.ui.recyclerview

import androidx.recyclerview.widget.RecyclerView
import com.example.tat.databinding.ProjectItemBinding
import com.example.tat.databinding.SuiteItemBinding
import com.example.tat.features.home.presentation.model.UserProjectUi
import com.example.tat.features.project.presentation.model.SuiteOfProject

class ViewHolderSuiteOfProject(val binding: SuiteItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(suiteOfProject: SuiteOfProject) {
        binding.projectName.text = suiteOfProject.suiteName
    }
}