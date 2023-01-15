package com.eray.horoscopeapp.ui.home

import android.os.Bundle
import android.view.View
import com.eray.horoscopeapp.R
import com.eray.horoscopeapp.databinding.FragmentHomeBinding
import com.eray.core.base.ui.BaseFragment


class HomeFragment : BaseFragment<FragmentHomeBinding>() {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun getFragmentView(): Int = R.layout.fragment_home


}