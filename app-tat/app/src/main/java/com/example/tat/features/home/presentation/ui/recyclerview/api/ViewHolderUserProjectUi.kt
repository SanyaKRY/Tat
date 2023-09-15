package com.example.tat.features.home.presentation.ui.recyclerview.api

import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.tat.databinding.MyLayoutBinding
import com.example.tat.databinding.ProjectItemBinding
import com.example.tat.features.home.presentation.model.UserProjectUi

class ViewHolderUserProjectUi(val binding: MyLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(userProject: UserProjectUi) {
        binding.projectName.text = userProject.projectName

        setTransitionNames(binding.projectId, binding.projectName, userProject)
    }

    private fun setTransitionNames(imageView: AppCompatImageView, textView: TextView, userProject: UserProjectUi) {
        imageView.transitionName = userProject.projectId.toString()
        textView.transitionName = userProject.projectName
    }
}