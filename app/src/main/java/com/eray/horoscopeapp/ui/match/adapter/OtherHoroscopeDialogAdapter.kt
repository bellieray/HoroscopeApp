package com.eray.horoscopeapp.ui.match.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eray.horoscopeapp.databinding.ItemOtherHoroscopeBinding
import com.eray.horoscopeapp.model.Horoscope
import com.eray.horoscopeapp.util.setBgWithId

class OtherHoroscopeDialogAdapter(val callback: (OtherHoroscope) -> Unit) :
    ListAdapter<OtherHoroscope, RecyclerView.ViewHolder>(DiffCallback) {
    object DiffCallback : DiffUtil.ItemCallback<OtherHoroscope>() {
        override fun areItemsTheSame(
            oldItem: OtherHoroscope,
            newItem: OtherHoroscope
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: OtherHoroscope,
            newItem: OtherHoroscope
        ): Boolean {
            return oldItem == newItem
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

    @SuppressLint("NotifyDataSetChanged")
    private fun changeBadgeChecked(id: Long?) {
        this.currentList.map { item ->
            item.isChecked = item.horoscope.id == id
        }
        notifyDataSetChanged()
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