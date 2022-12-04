package com.mami.horoscopeapp.ui.horoscope

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mami.horoscopeapp.databinding.ItemHoroscopeWidgetBinding


class HoroscopeAdapter : ListAdapter<PagerItem, RecyclerView.ViewHolder>(HoroscopeDiffCallback) {
    object HoroscopeDiffCallback : DiffUtil.ItemCallback<PagerItem>() {
        override fun areItemsTheSame(oldItem: PagerItem, newItem: PagerItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: PagerItem, newItem: PagerItem): Boolean {
            return oldItem == newItem
        }

    }

    inner class HoroscopeViewHolder(val binding: ItemHoroscopeWidgetBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PagerItem) {
            binding.horoscopeItem = item.name
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return HoroscopeViewHolder(
            ItemHoroscopeWidgetBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HoroscopeViewHolder) holder.bind(getItem(position))
    }
}