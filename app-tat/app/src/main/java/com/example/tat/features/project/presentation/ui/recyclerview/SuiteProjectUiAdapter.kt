package com.example.tat.features.project.presentation.ui.recyclerview

import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tat.R
import com.example.tat.databinding.DonutChartBinding
import com.example.tat.databinding.ProjectItemBinding
import com.example.tat.databinding.SuiteItemBinding
import com.example.tat.features.home.presentation.ui.recyclerview.api.ViewHolderUserProjectUi
import com.example.tat.features.project.presentation.model.SuiteOfProject
import com.example.tat.features.project.presentation.vm.ProjectViewModel
import javax.inject.Inject

class SuiteProjectUiAdapter constructor(
    private val view: View,
    private val projectViewModel: ProjectViewModel
) : ListAdapter<SuiteOfProject, ViewHolderSuiteOfProject>(
    SuiteUiDiffCallback()
) {

    private var selectable = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderSuiteOfProject {
        val itemViewHolder = SuiteItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)

        val viewHolder = ViewHolderSuiteOfProject(itemViewHolder)

        setItemListener(viewHolder)

        return viewHolder
    }

    private fun setItemListener(viewHolder: ViewHolderSuiteOfProject) {
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {


                view.isVisible  = true
                var suite: SuiteOfProject = getItem(position)
                projectViewModel.getChart(suite, 0)
//                viewHolder.itemView.background = view.context.getDrawable(R.color.red)
//                viewHolder.binding.projectName.setBackgroundColor(Color.RED)
//                viewHolder.binding.projectName.textSize = 20F
                viewHolder.binding.projectName.setTextColor(Color.RED)
                viewHolder.binding.projectName.setTypeface(null, Typeface.BOLD)

////                donutChartBinding.root.isVisible  = true
//
//                donutChartBinding.root.setOnClickListener {
//                    selectable = false
////                    donutChartBinding.root.isVisible  = false
//                    viewHolder.binding.transformationLayout.finishTransform()
//                }
//
//                // отсуда можем брать pass, failed, skiped
//                var suite: SuiteOfProject = getItem(position)
//
//                //становится выбранным
//                selectable = true
//
////                val pass = 32
////                val failed = 9
////                val skipped = 6
//                donutChartBinding.customView.setCurrent(suite.pass, suite.failed, suite.skipped)
//
//                viewHolder.binding.transformationLayout.bindTargetView(donutChartBinding.root)
//                viewHolder.binding.transformationLayout.startTransform()
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolderSuiteOfProject, position: Int) {
        holder.apply {
            val current: SuiteOfProject = getItem(position)
            bind(current)
        }
    }
}