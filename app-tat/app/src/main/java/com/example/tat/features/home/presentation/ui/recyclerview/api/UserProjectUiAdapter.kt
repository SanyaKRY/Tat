package com.example.tat.features.home.presentation.ui.recyclerview.api

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tat.databinding.MyLayoutBinding
import com.example.tat.databinding.ProjectItemBinding
import com.example.tat.features.home.presentation.model.CatUi
import com.example.tat.features.home.presentation.model.UserProjectUi
import com.example.tat.features.home.presentation.ui.recyclerview.database.ViewHolderCatUi
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import javax.inject.Inject

class UserProjectUiAdapter @AssistedInject constructor(
    @Assisted private val userProjectDetailListener: (
        userProjectUi: UserProjectUi, imageView: AppCompatImageView, textView: AppCompatTextView
    ) -> Unit
) : ListAdapter<UserProjectUi, ViewHolderUserProjectUi>(
    UserProjectUiDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderUserProjectUi {
        val itemViewHolder = MyLayoutBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)

        val viewHolder = ViewHolderUserProjectUi(itemViewHolder)

        setItemListener(viewHolder)

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolderUserProjectUi, position: Int) {
        holder.apply {
            val current: UserProjectUi = getItem(position)
            bind(current)
        }
    }

    private fun setItemListener(viewHolderUserProjectUi: ViewHolderUserProjectUi) {
        viewHolderUserProjectUi.itemView.setOnClickListener {
            val position = viewHolderUserProjectUi.bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                userProjectDetailListener.invoke(getItem(position),
                    viewHolderUserProjectUi.binding.projectId,
                            viewHolderUserProjectUi.binding.projectName)
            }
        }
    }
}