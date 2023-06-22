package com.eray.horoscopeapp.ui.tarot

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.eray.horoscopeapp.R
import com.eray.horoscopeapp.components.BaseVerticalDividerGridItemDecoration
import com.eray.horoscopeapp.databinding.FragmentTarotListBinding
import com.eray.horoscopeapp.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TarotListFragment : BaseFragment<FragmentTarotListBinding>() {
    private val tarotsViewModel: TarotListViewModel by viewModels()
    lateinit var tarotListAdapter: TarotListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tarotsViewModel.fetchTarots()
        initViews()
        initObservers()
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                tarotsViewModel.viewState.collect {
                    it.tarots?.let { tarots ->
                        tarotListAdapter.setList(tarots)
                    }
                    binding.isLoading = it.isLoading
                }
            }
        }
    }

    private fun initViews() = with(binding) {
        tarotListAdapter = TarotListAdapter()
        rvTarotList.layoutManager = GridLayoutManager(requireContext(),4)
        rvTarotList.adapter = tarotListAdapter
        rvTarotList.setHasFixedSize(true)
        rvTarotList.addItemDecoration(
            BaseVerticalDividerGridItemDecoration(
                rowSpacing = resources.getDimensionPixelSize(R.dimen.margin_5),
                columnSpacing = resources.getDimensionPixelSize(R.dimen.margin_5),
                edgeSpacingVertical = resources.getDimensionPixelSize(R.dimen.margin_10),
                edgeSpacingHorizontal = resources.getDimensionPixelSize(R.dimen.margin_10)
            )
        )
    }

    override fun getFragmentView() = R.layout.fragment_tarot_list
}