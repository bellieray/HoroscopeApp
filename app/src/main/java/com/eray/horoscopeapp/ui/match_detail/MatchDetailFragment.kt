package com.eray.horoscopeapp.ui.match_detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.eray.horoscopeapp.R
import com.eray.horoscopeapp.databinding.FragmentMatchDetailBinding
import com.eray.horoscopeapp.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MatchDetailFragment : BaseFragment<FragmentMatchDetailBinding>() {
    private val matchDetailViewModel by viewModels<MatchDetailViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initViews()
    }

    private fun initViews() {
        binding.icArrowLeft.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            matchDetailViewModel.viewState.collect {
                it.horoscopeMatchItem?.let { horoscopeItem ->
                    binding.matchingHoroscopeItem = horoscopeItem
                }

                binding.isLoading = it.isLoading
            }
        }
    }

    override fun getFragmentView() = R.layout.fragment_match_detail
}