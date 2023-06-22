package com.eray.horoscopeapp.ui.home

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.eray.horoscopeapp.R
import com.eray.horoscopeapp.databinding.FragmentHomeBinding
import com.eray.horoscopeapp.ui.base.BaseFragment
import com.eray.horoscopeapp.ui.decorations.BaseVerticalDividerItemDecoration
import com.eray.horoscopeapp.util.BackgroundImageConstants.BG_MOON_SIGN
import com.eray.horoscopeapp.util.BackgroundImageConstants.BG_NAME_FORTUNE
import com.eray.horoscopeapp.util.BackgroundImageConstants.BG_RISING_SIGN
import com.eray.horoscopeapp.util.BackgroundImageConstants.BG_SUN_SIGN
import com.eray.horoscopeapp.util.navigateWithPushAnimation

class HomeFragment : BaseFragment<FragmentHomeBinding>(), HomeListener {

    private val homeItemAdapter by lazy { HomeItemAdapter(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val homeItemList = mutableListOf(
            //HomeItem(HomeItemTitle.TAROT.ordinal, BG_TAROT, getString(R.string.tarot)),
            HomeItem(
                HomeItemTitle.RISING_SIGN.ordinal,
                BG_RISING_SIGN,
                getString(R.string.rising_sign)
            ),
            HomeItem(HomeItemTitle.MOON_SIGN.ordinal, BG_MOON_SIGN, getString(R.string.moon_sing)),
            HomeItem(HomeItemTitle.SUN_SIGN.ordinal, BG_SUN_SIGN, getString(R.string.sun_sign)),
            HomeItem(
                HomeItemTitle.NAME_FORTUNE.ordinal,
                BG_NAME_FORTUNE,
                getString(R.string.name_fortune)
            ),
            HomeItem(
                HomeItemTitle.TAROT.ordinal,
                BG_NAME_FORTUNE,
                getString(R.string.tarot)
            )
        )
        binding.rvHome.addItemDecoration(
            BaseVerticalDividerItemDecoration(
                requireContext(),
                paddingInResId = R.dimen.margin_20,
                paddingOutResId = R.dimen.margin_20,
                dismissOutTopPadding = false,
                dismissOutBottomPadding = false
            )
        )
        binding.rvHome.adapter = homeItemAdapter
        homeItemAdapter.submitList(homeItemList)
    }

    override fun getFragmentView(): Int = R.layout.fragment_home

    override fun onItemClicked(homeItem: HomeItem) {
        findNavController().navigateWithPushAnimation(
            when (homeItem.id) {
                //HomeItemTitle.TAROT.ordinal -> HomeFragmentDirections.actionHomeFragmentToTarotFragment()
                HomeItemTitle.MOON_SIGN.ordinal -> HomeFragmentDirections.actionHomeFragmentToCalculateMoonSignFragment()
                HomeItemTitle.SUN_SIGN.ordinal -> HomeFragmentDirections.actionHomeFragmentToCalculateSunSignFragment()
                HomeItemTitle.RISING_SIGN.ordinal -> HomeFragmentDirections.actionHomeFragmentToCalculateRisingSignFragment()
                HomeItemTitle.NAME_FORTUNE.ordinal -> HomeFragmentDirections.actionHomeFragmentToNameFortuneFragment()
                HomeItemTitle.TAROT.ordinal -> HomeFragmentDirections.actionHomeFragmentToTarotFragment()
                else -> HomeFragmentDirections.actionHomeFragmentToProfileFragment2()
            }
        )
    }
}