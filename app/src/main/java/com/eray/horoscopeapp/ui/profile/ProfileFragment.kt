package com.eray.horoscopeapp.ui.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.eray.horoscopeapp.R
import com.eray.horoscopeapp.databinding.FragmentProfileBinding
import com.eray.horoscopeapp.model.Horoscope
import com.eray.horoscopeapp.ui.SessionViewModel
import com.eray.horoscopeapp.ui.base.BaseFragment
import com.eray.horoscopeapp.ui.profile.adapter.ProfileAdapter
import com.eray.horoscopeapp.util.navigateWithPushAnimation
import com.eray.horoscopeapp.util.navigateWithPushAnimationAndPop
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>() {
    private val sessionViewModel by activityViewModels<SessionViewModel>()
    private val adapter = ProfileAdapter()
    private val profileViewModel by viewModels<ProfileViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //profileViewModel.setUserInfoModel(sessionViewModel.viewState.value.personalDetail)
        initViews()
        initObservers()
    }

    private fun initViews() {
        binding.vpProfile.adapter = this.adapter
        binding.diProfile.attachTo(binding.vpProfile)
        binding.tvLogout.setOnClickListener {
            profileViewModel.clearAllValues()
            findNavController().navigateWithPushAnimationAndPop(
                ProfileFragmentDirections.actionProfileFragmentToLoginFragment(),
                R.id.loginFragment,
                isInclusive = true
            )
        }
        binding.tvPersonalDetails.setOnClickListener {
            findNavController().navigateWithPushAnimation(
                ProfileFragmentDirections.actionProfileFragmentToUserPersonalDetailFragment(
                    personalDetail = profileViewModel.viewState.value.personalDetail
                )
            )
        }
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            launch {
                profileViewModel.viewState.collect { profileViewState ->
                    profileViewState.horoscopeFromId?.let {
                        binding.player = it
                        profileViewModel.setPagerItemList(preparePagerList(it.horoscope))
                    }

                    profileViewState.pagerItems?.let {
                        adapter.submitList(it)
                    }
                }
            }

            launch {
                sessionViewModel.viewState.collect {
                    binding.tvUsername.text = it.personalDetail?.name
                    profileViewModel.setUserInfoModel(it.personalDetail)
                }
            }
        }
    }

    private fun preparePagerList(horoscope: Horoscope): List<PagerItem> {
        val list = buildList {
            add(PagerItem(0, prepareFirstPageContentItems(horoscope)))
            add(PagerItem(1, prepareSecondPageContentItems(horoscope)))
        }
        return list
    }

    private fun prepareFirstPageContentItems(horoscope: Horoscope): List<PageContent> {

        val list = buildList {
            add(PageContent(0, "Element", horoscope.element))
            //  add(PageContent(1, "Tarot Kartı", "Tarot Kartı"))
            add(PageContent(2, "Home", horoscope.home))
            add(PageContent(3, "Planet", horoscope.planet))
            add(PageContent(4, "Lucky Dates", horoscope.luckyNumbers))
        }
        return list
    }

    private fun prepareSecondPageContentItems(horoscope: Horoscope): List<PageContent> {
        val list = buildList {
            add(PageContent(5, "Lucky Days", horoscope.luckyDay))
            add(PageContent(6, "Compatible Horoscope", horoscope.compatibleHoroscopes))
            add(PageContent(7, "Incompatible Horoscope", horoscope.incompatibleHoroscopes))
            add(
                PageContent(
                    8, "Which celebrities has same horoscope",
                    horoscope.foreignCelebrities
                )
            )
            // add(PageContent(9, "Renk", horoscope.color))
        }
        return list
    }

    override fun getFragmentView() = R.layout.fragment_profile
}

data class PagerItem(val id: Int, val pageContents: List<PageContent>)
data class PageContent(val id: Int, val title: String? = null, val content: String?)