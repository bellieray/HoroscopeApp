package com.eray.horoscopeapp.ui.match

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.eray.horoscopeapp.R
import com.eray.horoscopeapp.databinding.FragmentMatchBinding
import com.eray.horoscopeapp.ui.SessionViewModel
import com.eray.horoscopeapp.ui.base.BaseFragment
import com.eray.horoscopeapp.ui.match.adapter.OtherHoroscope
import com.eray.horoscopeapp.ui.match.dialog.OtherHoroscopeDialog
import com.eray.horoscopeapp.util.ConnectionUtils
import com.eray.horoscopeapp.util.navigateWithPushAnimation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.IOException


@AndroidEntryPoint
class MatchFragment : BaseFragment<FragmentMatchBinding>() {
    private val matchViewModel by viewModels<MatchViewModel>()
    private val sessionViewModel by activityViewModels<SessionViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        matchViewModel.fetchHoroscopes()
        initObservers()
        initViews()
    }

    private fun initViews() {
        binding.clMale.setOnClickListener {
            it.isSelected = true
            binding.clFemale.isSelected = !it.isSelected
        }

        binding.clFemale.setOnClickListener {
            it.isSelected = true
            binding.clMale.isSelected = !it.isSelected
        }

        binding.clOtherSign.setOnClickListener {
            matchViewModel.viewState.value.horoscopeList?.let {
                showDialog(it)
            }
        }

        binding.btnCheckCompability.setOnClickListener {
            if (binding.tvHoroscopePlayer.text.isNullOrEmpty()
                    .not() && binding.tvYourHoroscope.text.isNullOrEmpty()
                    .not()
            ) {
                val firstId =
                    matchViewModel.viewState.value.horoscopeFromId?.horoscope?.id?.toInt() ?: 0
                val secondId =
                    matchViewModel.viewState.value.otherHoroscope?.horoscope?.id?.toInt() ?: 0
                findNavController().navigateWithPushAnimation(
                    MatchFragmentDirections.actionMatchFragmentToMatchDetailFragment(
                        firstId,
                        secondId
                    )
                )
            } else {
                handleError(IOException(), requireActivity())
            }
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
                    matchState.otherHoroscope?.let {
                        binding.otherPlayer = it
                    }

                    matchState.horoscopeFromId?.let {
                        binding.player = it
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