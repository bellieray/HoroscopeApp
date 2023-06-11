package com.eray.horoscopeapp.ui.horoscope

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.eray.horoscopeapp.R
import com.eray.horoscopeapp.databinding.FragmentHoroscopeBinding
import com.eray.horoscopeapp.ui.base.BaseFragment
import com.eray.horoscopeapp.util.BackgroundImageConstants.HOROSCOPE_FIRST_BG
import com.eray.horoscopeapp.util.BackgroundImageConstants.HOROSCOPE_SECOND_BG
import com.eray.horoscopeapp.util.navigateWithPushAnimation
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HoroscopeFragment : BaseFragment<FragmentHoroscopeBinding>() {
    private val horoscopeViewModel by viewModels<HoroscopeViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        horoscopeViewModel.fetchHoroscopes()
    }

    private fun initViews() {
        binding.firstImageUrl = HOROSCOPE_FIRST_BG
        binding.secondImageUrl = HOROSCOPE_SECOND_BG
        binding.clFirst.setOnClickListener {
            findNavController().navigateWithPushAnimation(
                HoroscopeFragmentDirections.actionHoroscopeFragmentToHoroscopeList(
                    false
                )
            )
        }
        binding.clSecond.setOnClickListener {
            findNavController().navigateWithPushAnimation(
                HoroscopeFragmentDirections.actionHoroscopeFragmentToHoroscopeList(
                    true
                )
            )
        }
        binding.executePendingBindings()
    }

    override fun getFragmentView() = R.layout.fragment_horoscope
}