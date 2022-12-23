package com.eray.horoscopeapp.ui.horoscope

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eray.horoscopeapp.databinding.ItemHoroscopeWidgetBinding
import com.eray.horoscopeapp.model.Horoscope


class HoroscopeAdapter : ListAdapter<Horoscope, RecyclerView.ViewHolder>(HoroscopeDiffCallback) {
    object HoroscopeDiffCallback : DiffUtil.ItemCallback<Horoscope>() {
        override fun areItemsTheSame(oldItem: Horoscope, newItem: Horoscope): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Horoscope, newItem: Horoscope): Boolean {
            return oldItem == newItem
        }

    }

    inner class HoroscopeViewHolder(val binding: ItemHoroscopeWidgetBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Horoscope) {
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