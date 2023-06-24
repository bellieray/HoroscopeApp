package com.eray.horoscopeapp.ui.tarot

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eray.horoscopeapp.databinding.ItemTarotPagerBinding
import com.eray.horoscopeapp.model.Tarot

class TarotAdapter : ListAdapter<Tarot, RecyclerView.ViewHolder>(Diffcallback) {
    object Diffcallback : DiffUtil.ItemCallback<Tarot>() {
        override fun areItemsTheSame(oldItem: Tarot, newItem: Tarot): Boolean {
          return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Tarot, newItem: Tarot): Boolean {
            return oldItem == newItem
        }

    }

    inner class TarotPagerViewHolder(val binding: ItemTarotPagerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tarot: Tarot) {
            binding.imageUrl = tarot.imageUrl
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return TarotPagerViewHolder(ItemTarotPagerBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is TarotPagerViewHolder) holder.bind(getItem(position))
    }
}