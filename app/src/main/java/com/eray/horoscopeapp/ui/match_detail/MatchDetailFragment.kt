package com.eray.horoscopeapp.ui.match_detail

import android.os.Bundle
import android.view.View
import com.eray.horoscopeapp.R
import com.eray.horoscopeapp.databinding.FragmentMatchDetailBinding
import com.eray.horoscopeapp.ui.base.BaseFragment

class MatchDetailFragment : BaseFragment<FragmentMatchDetailBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun getFragmentView() = R.layout.fragment_match_detail
}