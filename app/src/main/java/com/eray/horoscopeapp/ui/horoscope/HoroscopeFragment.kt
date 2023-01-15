package com.eray.horoscopeapp.ui.horoscope

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.eray.horoscopeapp.R
import com.eray.horoscopeapp.databinding.FragmentHoroscopeBinding
import com.eray.core.base.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

private const val NEXT_ITEM_VISIBLE_RATIO = 0.186f
private const val CURRENT_ITEM_HORIZONTAL_MARGIN_RATIO = 0.032f

@AndroidEntryPoint
class HoroscopeFragment : BaseFragment<FragmentHoroscopeBinding>() {
    private val horoscopeViewModel by viewModels<HoroscopeViewModel>()
    private val horoscopeAdapter = HoroscopeAdapter()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            horoscopeViewModel.viewState.collect { viewState ->
                viewState.horoscopeList?.let {
                    horoscopeAdapter.submitList(it)
                }
            }
        }
    }

    private fun initViews() {
        binding.vpHoroscope.adapter = horoscopeAdapter
        binding.vpHoroscope.offscreenPageLimit = 1
        val deviceWidth = binding.root.context.resources.displayMetrics.widthPixels
        val nextItemVisiblePx = deviceWidth * NEXT_ITEM_VISIBLE_RATIO
        val currentItemHorizontalMarginPx =
            deviceWidth * CURRENT_ITEM_HORIZONTAL_MARGIN_RATIO + 185
        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
        val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
            page.translationX = -pageTranslationX * position
            page.scaleY = 1 - (0.10f * kotlin.math.abs(position))
        }
        binding.vpHoroscope.setPageTransformer(pageTransformer)
        binding.executePendingBindings()
    }

    override fun getFragmentView() = R.layout.fragment_horoscope
}