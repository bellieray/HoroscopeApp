package com.eray.horoscopeapp.ui.match_detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.eray.horoscopeapp.R
import com.eray.horoscopeapp.databinding.FragmentMatchDetailBinding
import com.eray.horoscopeapp.ui.SessionViewModel
import com.eray.horoscopeapp.ui.base.BaseFragment
import com.eray.horoscopeapp.util.DialogUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MatchDetailFragment : BaseFragment<FragmentMatchDetailBinding>() {
    private val matchDetailViewModel by viewModels<MatchDetailViewModel>()
    private val sessionViewModel: SessionViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initViews()
        matchDetailViewModel.getMatchingHoroscopeItem(sessionViewModel.viewState.value.isEnglish == true)
    }

    private fun initViews() {
        binding.icArrowLeft.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.tvDescriptionText.setOnClickListener {
            matchDetailViewModel.viewState.value.horoscopeMatchItem?.relationText?.let { it1 ->
                DialogUtils.showMatchingDescriptionTextDetailDialog(
                    requireContext(),
                    it1
                )
            }
        }
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            matchDetailViewModel.viewState.collect { it ->
                it.horoscopeMatchItem?.let { horoscopeItem ->
                    binding.matchingHoroscopeItem = horoscopeItem
                }

                it.consumableErrors?.firstOrNull()?.let { error ->
                    handleError(error.exception, requireActivity())
                    matchDetailViewModel.errorConsumed(error.id)
                }

                binding.isLoading = it.isLoading
            }
        }
    }

    override fun getFragmentView() = R.layout.fragment_match_detail
}