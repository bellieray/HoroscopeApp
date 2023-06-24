package com.eray.horoscopeapp.ui.tarot

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.eray.horoscopeapp.R
import com.eray.horoscopeapp.databinding.FragmentTarotBinding
import com.eray.horoscopeapp.ui.base.BaseFragment

private const val NEXT_ITEM_VISIBLE_RATIO = 0.1f
private const val CURRENT_ITEM_HORIZONTAL_MARGIN_RATIO = 0.1f

class TarotFragment : BaseFragment<FragmentTarotBinding>() {
    private val tarotPagetAdapter: TarotAdapter by lazy {
        TarotAdapter()
    }

    private val tarotArgs: TarotFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        binding.vpTarot.offscreenPageLimit =
            3 // Number of offscreen pages to retain on each side of the current page

// Set a custom PageTransformer
        binding.vpTarot.offscreenPageLimit = 1
        val deviceWidth = binding.root.context.resources.displayMetrics.widthPixels
        val nextItemVisiblePx = deviceWidth * NEXT_ITEM_VISIBLE_RATIO
        val currentItemHorizontalMarginPx = deviceWidth * CURRENT_ITEM_HORIZONTAL_MARGIN_RATIO
        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
        val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
            page.translationX = -pageTranslationX * position
            page.scaleY = 1 - (0.10f * kotlin.math.abs(position))
        }
        binding.vpTarot.setPageTransformer(pageTransformer)
        binding.vpTarot.adapter = tarotPagetAdapter
        tarotPagetAdapter.submitList(tarotArgs.tarotListItem?.list)

        binding.vpTarot.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                binding.tvTitle.text = tarotArgs.tarotListItem?.list?.get(position)?.name
                binding.tvDescription.text = tarotArgs.tarotListItem?.list?.get(position)?.description
            }
        })
    }

    override fun getFragmentView(): Int = R.layout.fragment_tarot
}
