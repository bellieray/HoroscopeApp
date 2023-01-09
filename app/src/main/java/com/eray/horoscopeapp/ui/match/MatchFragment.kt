package com.eray.horoscopeapp.ui.match

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.eray.horoscopeapp.R
import com.eray.horoscopeapp.databinding.FragmentMatchBinding
import com.eray.horoscopeapp.model.Horoscope
import com.eray.horoscopeapp.model.Result
import com.eray.horoscopeapp.ui.SessionViewModel
import com.eray.horoscopeapp.ui.base.BaseFragment
import com.eray.horoscopeapp.ui.match.adapter.OtherHoroscope
import com.eray.horoscopeapp.ui.match.dialog.OtherHoroscopeDialog
import com.eray.horoscopeapp.util.setBgWithId
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MatchFragment : BaseFragment<FragmentMatchBinding>() {
    private val matchViewModel by viewModels<MatchViewModel>()
    private val sessionViewModel by activityViewModels<SessionViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initViews()
    }

    private fun initViews() {
        binding.clOtherSign.setOnClickListener {
            matchViewModel.fetchHoroscopes()
        }
        binding.clMale.setOnClickListener {
            it.isSelected = true
            binding.clFemale.isSelected = !it.isSelected
        }

        binding.clFemale.setOnClickListener {
            it.isSelected = true
            binding.clMale.isSelected = !it.isSelected
        }
    }


    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            matchViewModel.setUserInfoModel(sessionViewModel.viewState.value.personalDetail)
            lifecycleScope.launch {
                sessionViewModel.viewState.collect { viewState ->
                    viewState.personalDetail?.let {
                        if (it.gender == "Erkek") binding.ivGender.setBackgroundResource(R.drawable.ic_man) else binding.ivGender.setBackgroundResource(
                            R.drawable.ic_woman
                        )
                    }
                }
            }

            lifecycleScope.launch {
                matchViewModel.viewState.collect { matchState ->
                    matchState.horoscope?.let { horoscope ->
                        binding.tvYourHoroscope.text = horoscope.first
                        binding.ivHoroscope.setBackgroundResource(horoscope.second)
                    }
                    matchState.horoscopeList?.let {
                        showDialog(it)
                        matchViewModel.listConsumed()
                    }

                    matchState.otherHoroscope?.let {
                        binding.ivHoroscopePlayer.setBgWithId(it.horoscope.id)
                        binding.tvHoroscopePlayer.text = it.horoscope.name
                    }
                }
            }

        }

    }

    private fun showDialog(list: List<OtherHoroscope>) {
        val dialog = OtherHoroscopeDialog(list, ::setOtherHoroscopeModelFromSheet)
        dialog.show(childFragmentManager, OtherHoroscopeDialog::class.java.simpleName)
    }

    private fun setOtherHoroscopeModelFromSheet(otherHoroscope: OtherHoroscope) {
        matchViewModel.setBottomSheetModel(otherHoroscope)
    }

    override fun getFragmentView() = R.layout.fragment_match

}