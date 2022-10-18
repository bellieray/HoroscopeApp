package com.mami.horoscopeapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mami.horoscopeapp.R
import com.mami.horoscopeapp.databinding.FragmentHomeBinding
import com.mami.horoscopeapp.ui.base.BaseFragment


class HomeFragment : BaseFragment<FragmentHomeBinding>() {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun getFragmentView(): Int = R.layout.fragment_home


}