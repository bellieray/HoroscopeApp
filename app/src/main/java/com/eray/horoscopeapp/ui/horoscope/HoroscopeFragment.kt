package com.eray.horoscopeapp.ui.horoscope

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.eray.horoscopeapp.R
import com.eray.horoscopeapp.databinding.FragmentHoroscopeBinding
import com.eray.horoscopeapp.ui.base.BaseFragment
import com.eray.horoscopeapp.util.navigateWithPushAnimation
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
        binding.firstImageUrl =
            "https://images.pexels.com/photos/16922371/pexels-photo-16922371/free-photo-of-art-vintage-architecture-travel.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"
        binding.secondImageUrl =
            "https://images.pexels.com/photos/6160299/pexels-photo-6160299.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"
        binding.clFirst.setOnClickListener {
            findNavController().navigateWithPushAnimation(HoroscopeFragmentDirections.actionHoroscopeFragmentToHoroscopeList())
        }
        binding.clSecond.setOnClickListener {
        }
        binding.executePendingBindings()
    }

    override fun getFragmentView() = R.layout.fragment_horoscope
}