package com.eray.horoscopeapp.ui.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.eray.horoscopeapp.R
import com.eray.horoscopeapp.databinding.FragmentProfileBinding
import com.eray.horoscopeapp.ui.SessionViewModel
import com.eray.core.base.ui.BaseFragment
import com.eray.horoscopeapp.util.checkHoroscope
import com.eray.horoscopeapp.util.checkHoroscopeProperties

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {
    private val sessionViewModel by activityViewModels<SessionViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
    }

    private fun initViews() {

    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            sessionViewModel.viewState.collect {
                binding.tvUsername.text = it.personalDetail?.name?.capitalize()
                binding.tvUserHoroscope.text = it.personalDetail?.birthTime?.checkHoroscope()?.first
                with(it.personalDetail?.birthTime?.checkHoroscope()?.first?.checkHoroscopeProperties()) {
                    binding.tvLuckyDays.text = this?.luckyDay
                    binding.tvColor.text = this?.color
                    binding.tvElement.text = this?.element
                    binding.tvLuckyDates.text = this?.date
                }
                it.personalDetail?.birthTime?.checkHoroscope()?.second?.let { it1 ->
                    binding.ivUserHoroscope.setBackgroundResource(
                        it1
                    )
                }
            }
        }
    }

    override fun getFragmentView() = R.layout.fragment_profile
}