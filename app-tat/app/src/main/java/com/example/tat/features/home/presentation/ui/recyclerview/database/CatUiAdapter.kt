package com.example.tat.features.home.presentation.ui.recyclerview.database

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tat.databinding.CatItemBinding
import com.example.tat.features.home.presentation.model.CatUi
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

class CatUiAdapter @AssistedInject constructor(
    @Assisted private val catUiDetailListener: (
        catUi: CatUi
    ) -> Unit
) : ListAdapter<CatUi, ViewHolderCatUi>(CatUiDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderCatUi {
        val itemViewHolder = CatItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)

        val viewHolderCatUi = ViewHolderCatUi(itemViewHolder)

        setItemListener(viewHolderCatUi)

        return viewHolderCatUi
    }

    override fun onBindViewHolder(holder: ViewHolderCatUi, position: Int) {
        holder.apply {
            val current: CatUi = getItem(position)
            bind(current)
        }
    }

    private fun setItemListener(viewHolderCatUi: ViewHolderCatUi) {
        viewHolderCatUi.itemView.setOnClickListener {
            val position = viewHolderCatUi.bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                catUiDetailListener.invoke(getItem(position))
            }
        }
    }
}