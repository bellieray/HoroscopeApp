package com.eray.horoscopeapp.ui.base_horoscope_list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.eray.horoscopeapp.R
import com.eray.horoscopeapp.databinding.FragmentHoroscopeListBinding
import com.eray.horoscopeapp.model.Horoscope
import com.eray.horoscopeapp.ui.base.BaseFragment
import com.eray.horoscopeapp.ui.horoscope.HoroscopeAdapter
import com.eray.horoscopeapp.ui.horoscope.HoroscopeListener
import com.eray.horoscopeapp.ui.horoscope.HoroscopeViewModel
import com.eray.horoscopeapp.util.navigateWithPushAnimation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HoroscopeList : BaseFragment<FragmentHoroscopeListBinding>(), HoroscopeListener {
    val adapter = HoroscopeAdapter(this)
    private val horoscopeViewModel by activityViewModels<HoroscopeViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
        horoscopeViewModel.fetchHoroscopes()
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            horoscopeViewModel.viewState.collect { viewState ->
                viewState.horoscopeList?.let {
                    adapter.submitList(it)
                }
            }
        }
    }

    private fun initViews() {
        binding.vpHoroscopeList.adapter = this.adapter
        binding.btnHoroscopeList.setOnClickListener {
            findNavController().navigateWithPushAnimation(
                HoroscopeListDirections.actionHoroscopeListToHoroscopeDetailFragment(
                    horoscope = horoscopeViewModel.viewState.value.currentHoroscope
                )
            )
        }
        binding.icArrowBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }


    override fun getFragmentView() = R.layout.fragment_horoscope_list
    override fun onGoesNextItem(position: Int) {
        binding.vpHoroscopeList.currentItem = position + 1
    }

    override fun onGoesPreviousItem(position: Int) {
        binding.vpHoroscopeList.currentItem = position - 1
    }

    override fun keepCurrentPosition(horoscope: Horoscope) {
        horoscopeViewModel.setCurrentPosition(horoscope)
    }
}