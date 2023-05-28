package com.eray.horoscopeapp.ui.tarot

import android.os.Bundle
import android.view.View
import com.eray.horoscopeapp.R
import com.eray.horoscopeapp.databinding.FragmentTarotBinding
import com.eray.horoscopeapp.ui.base.BaseFragment

class TarotFragment : BaseFragment<FragmentTarotBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
    override fun getFragmentView(): Int = R.layout.fragment_tarot
}