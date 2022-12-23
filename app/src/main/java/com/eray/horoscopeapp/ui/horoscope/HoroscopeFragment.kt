package com.eray.horoscopeapp.ui.horoscope

import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.eray.horoscopeapp.R
import com.eray.horoscopeapp.databinding.FragmentHoroscopeBinding
import com.eray.horoscopeapp.ui.base.BaseFragment

private const val NEXT_ITEM_VISIBLE_RATIO = 0.186f
private const val CURRENT_ITEM_HORIZONTAL_MARGIN_RATIO = 0.032f

class HoroscopeFragment : BaseFragment<FragmentHoroscopeBinding>() {
    private val horoscopeAdapter = HoroscopeAdapter()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        binding.vpHoroscope.adapter = horoscopeAdapter
        horoscopeAdapter.submitList(createMockforViewPager())
        binding.vpHoroscope.offscreenPageLimit = 1
        val deviceWidth = binding.root.context.resources.displayMetrics.widthPixels
        val nextItemVisiblePx = deviceWidth * NEXT_ITEM_VISIBLE_RATIO
        val currentItemHorizontalMarginPx =
            deviceWidth * CURRENT_ITEM_HORIZONTAL_MARGIN_RATIO
        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
        val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
            page.translationX = -pageTranslationX * position
            page.scaleY = 1 - (0.10f * kotlin.math.abs(position))
        }
        binding.vpHoroscope.setPageTransformer(pageTransformer)
        binding.executePendingBindings()
    }

    private fun createMockforViewPager(): List<PagerItem> {
        val list = mutableListOf<PagerItem>()
        list.add(PagerItem("İkizler"))
        list.add(PagerItem("Oğlak"))
        list.add(PagerItem("Başak"))
        list.add(PagerItem("Yengeç"))
        list.add(PagerItem("Terazi"))
        list.add(PagerItem("Aslan"))
        return list
    }

    override fun getFragmentView() = R.layout.fragment_horoscope
}

data class PagerItem(
    val name: String
)