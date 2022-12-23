package com.eray.horoscopeapp.ui.home

import android.os.Bundle
import android.view.View
import com.eray.horoscopeapp.R
import com.eray.horoscopeapp.databinding.FragmentHomeBinding
import com.eray.horoscopeapp.ui.base.BaseFragment


class HomeFragment : BaseFragment<FragmentHomeBinding>() {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun getFragmentView(): Int = R.layout.fragment_home


}