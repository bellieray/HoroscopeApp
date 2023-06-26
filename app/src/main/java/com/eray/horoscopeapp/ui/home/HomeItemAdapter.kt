package com.eray.horoscopeapp.ui.home

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eray.horoscopeapp.databinding.ItemHomeBinding

class HomeItemAdapter(private val listener: HomeListener) :
    ListAdapter<HomeItem, HomeItemAdapter.HomeItemViewHolder>(HomeItemAdapterDiffUtil()) {

    class HomeItemAdapterDiffUtil : DiffUtil.ItemCallback<HomeItem>() {
        override fun areItemsTheSame(oldItem: HomeItem, newItem: HomeItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: HomeItem, newItem: HomeItem): Boolean {
            return oldItem.title == newItem.title
        }
    }

    inner class HomeItemViewHolder(val binding: ItemHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(homeItem: HomeItem, listener: HomeListener) {
            binding.homeItem = homeItem
            binding.root.setOnClickListener {
                listener.onItemClicked(homeItem)
            }
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeItemViewHolder {
        return HomeItemViewHolder(
            ItemHomeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HomeItemViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }
}

data class HomeItem(
    val id: Int? = null,
    val backgroundImage: Drawable? = null,
    val title: String? = null
)

enum class HomeItemTitle {
    TAROT, MOON_SIGN, SUN_SIGN, RISING_SIGN, NAME_FORTUNE
}

interface HomeListener {
    fun onItemClicked(homeItem: HomeItem)
}