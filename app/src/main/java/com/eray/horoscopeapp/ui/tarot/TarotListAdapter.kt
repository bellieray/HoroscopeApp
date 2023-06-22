package com.eray.horoscopeapp.ui.tarot

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eray.horoscopeapp.databinding.ItemTarotBackBinding
import com.eray.horoscopeapp.model.Tarot

class TarotListAdapter : RecyclerView.Adapter<TarotListAdapter.TarotViewHolder>() {
    internal var list = ArrayList<Tarot>()

    inner class TarotViewHolder(val binding: ItemTarotBackBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tarot: Tarot) {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TarotViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return TarotViewHolder(ItemTarotBackBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setList(list: List<Tarot>) {
        this.list = list as ArrayList<Tarot>
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: TarotViewHolder, position: Int) {
        holder.bind(list[position])
    }
}