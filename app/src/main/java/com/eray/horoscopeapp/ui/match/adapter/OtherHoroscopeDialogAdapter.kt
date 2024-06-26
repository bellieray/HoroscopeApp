package com.eray.horoscopeapp.ui.match.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eray.horoscopeapp.databinding.ItemOtherHoroscopeBinding
import com.eray.horoscopeapp.model.Horoscope

class OtherHoroscopeDialogAdapter(val callback: (OtherHoroscope) -> Unit) :
    ListAdapter<OtherHoroscope, RecyclerView.ViewHolder>(DiffCallback) {
    object DiffCallback : DiffUtil.ItemCallback<OtherHoroscope>() {
        override fun areItemsTheSame(
            oldItem: OtherHoroscope,
            newItem: OtherHoroscope
        ): Boolean {
            return oldItem.isChecked == newItem.isChecked && oldItem.horoscope.id == newItem.horoscope.id
        }

        override fun areContentsTheSame(
            oldItem: OtherHoroscope,
            newItem: OtherHoroscope
        ): Boolean {
            return oldItem.horoscope.id== newItem.horoscope.id && oldItem.horoscope.imageUrl == newItem.horoscope.imageUrl
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return OtherHoroscopeViewHolder(
            ItemOtherHoroscopeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    private fun changeBadgeChecked(id: Long?) {
        this.currentList.forEachIndexed { index, item ->
            val newState = item.horoscope.id == id
            if (newState != item.isChecked) {
                item.isChecked = newState
                notifyItemChanged(index)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is OtherHoroscopeViewHolder -> holder.bind(getItem(position))
        }
    }

    inner class OtherHoroscopeViewHolder(val binding: ItemOtherHoroscopeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(otherHoroscope: OtherHoroscope) {
            binding.otherHoroscope = otherHoroscope
            binding.clContainer.isSelected = otherHoroscope.isChecked
            binding.root.setOnClickListener {
                changeBadgeChecked(otherHoroscope.horoscope.id)
                callback.invoke(otherHoroscope)
            }
            binding.executePendingBindings()
        }
    }
}

data class OtherHoroscope(val horoscope: Horoscope, var isChecked: Boolean = false) {
    companion object {
        fun from(horoscope: Horoscope): OtherHoroscope {
            return OtherHoroscope(horoscope, false)
        }
    }
}