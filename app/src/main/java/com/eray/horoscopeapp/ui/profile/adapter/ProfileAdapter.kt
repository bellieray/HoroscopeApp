package com.eray.horoscopeapp.ui.profile.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eray.horoscopeapp.R
import com.eray.horoscopeapp.components.BaseVerticalDividerGridItemDecoration
import com.eray.horoscopeapp.databinding.LayoutProfileHoroscopeFeatureBinding
import com.eray.horoscopeapp.ui.profile.PagerItem

class ProfileAdapter : ListAdapter<PagerItem, ProfileAdapter.ProfileViewHolder>(ProfileDiffUtil) {

    object ProfileDiffUtil : DiffUtil.ItemCallback<PagerItem>() {
        override fun areItemsTheSame(
            oldItem: PagerItem,
            newItem: PagerItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: PagerItem,
            newItem: PagerItem
        ): Boolean {
            return oldItem.id == newItem.id
        }
    }

    inner class ProfileViewHolder(val binding: LayoutProfileHoroscopeFeatureBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: PagerItem) {
            val innerAdapter = ProfileInnerAdapter()
            val resources = binding.root.context.resources
            binding.rvFeature.adapter = innerAdapter
            binding.rvFeature.addItemDecoration(
                BaseVerticalDividerGridItemDecoration(
                    edgeSpacingHorizontal = resources.getDimensionPixelSize(R.dimen.margin_10),
                    edgeSpacingVertical = resources.getDimensionPixelSize(R.dimen.margin_10),
                    rowSpacing = resources.getDimensionPixelSize(R.dimen.margin_10),
                    columnSpacing = resources.getDimensionPixelSize(R.dimen.margin_10)
                )
            )
            innerAdapter.submitList(model.pageContents)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        return ProfileViewHolder(
            LayoutProfileHoroscopeFeatureBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

data class UserHoroscopeProperties(
    val propertyImage: String,
    val propertyTitle: String,
    val propertyOptions: String
)
