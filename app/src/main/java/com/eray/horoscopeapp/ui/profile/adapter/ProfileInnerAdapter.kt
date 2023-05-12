package com.eray.horoscopeapp.ui.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eray.horoscopeapp.databinding.RowItemProfileHoroscopeFeatureBinding
import com.eray.horoscopeapp.ui.profile.PageContent

class ProfileInnerAdapter :
    ListAdapter<PageContent, RecyclerView.ViewHolder>(ProfileInnerDiffutil) {
    object ProfileInnerDiffutil : DiffUtil.ItemCallback<PageContent>() {
        override fun areItemsTheSame(oldItem: PageContent, newItem: PageContent): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: PageContent, newItem: PageContent): Boolean {
            return oldItem.title == newItem.title && oldItem.content == newItem.content
        }

    }

    inner class ProfileInnerViewHolder(val binding: RowItemProfileHoroscopeFeatureBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: PageContent) {
            binding.featureProperty.text = model.content
            binding.featureTitle.text = model.title
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ProfileInnerViewHolder(
            RowItemProfileHoroscopeFeatureBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ProfileInnerViewHolder) {
            holder.bind(getItem(position))
        }
    }
}