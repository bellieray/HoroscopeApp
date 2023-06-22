package com.eray.horoscopeapp.ui.tarot

import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.eray.horoscopeapp.R
import com.eray.horoscopeapp.databinding.FragmentTarotBinding
import com.eray.horoscopeapp.model.Tarot
import com.eray.horoscopeapp.ui.base.BaseFragment
import com.eray.horoscopeapp.util.navigateWithPushAnimation

class TarotFragment : BaseFragment<FragmentTarotBinding>() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivAdd.setOnClickListener {
            findNavController().navigateWithPushAnimation(TarotFragmentDirections.actionTarotFragmentToTarotListFragment())
        }

        setFragmentResultListener("SELECTED_ITEMS") { _, bundle ->
            val selectedItems = bundle.getSerializable("SELECTED_TAROTS") as? List<*>?
            binding.firstImage = (selectedItems as? List<Tarot>?)?.get(0)?.imageUrl
            binding.secondImage = (selectedItems as? List<Tarot>?)?.get(1)?.imageUrl
            binding.thirdImage = (selectedItems as? List<Tarot>?)?.get(2)?.imageUrl
        }
    }

    override fun getFragmentView(): Int = R.layout.fragment_tarot
}