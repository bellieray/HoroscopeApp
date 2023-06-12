package com.eray.horoscopeapp.ui.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.eray.horoscopeapp.R
import com.eray.horoscopeapp.databinding.FragmentProfileBinding
import com.eray.horoscopeapp.ui.SessionViewModel
import com.eray.horoscopeapp.ui.base.BaseFragment
import com.eray.horoscopeapp.ui.profile.adapter.ProfileAdapter
import com.eray.horoscopeapp.util.navigateWithPushAnimation
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
        adapter.submitList(preparePagerList())
        binding.diProfile.attachTo(binding.vpProfile)
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

    private fun preparePagerList(): List<PagerItem> {
        val list = buildList {
            add(PagerItem(prepareFirstPageContentItems()))
            add(PagerItem(prepareSecondPageContentItems()))
        }
        return list
    }

    private fun prepareFirstPageContentItems(): List<PageContent> {
        val list = buildList {
            add(PageContent("", "Element", "Ateş"))
            add(PageContent("", "Tarot Kartı", "Tarot Kartı"))
            add(PageContent("", "Ev", "Ev"))
            add(PageContent("", "Yönetici Gezegen", "Yönetici Gezegen"))
            add(PageContent("", "Şanslı Tarihler", "Şanslı Tarihler"))
        }
        return list
    }

    private fun prepareSecondPageContentItems(): List<PageContent> {
        val list = buildList {
            add(PageContent("", "Şanslı Günler", "Salı, Çarşamba, Perşembe"))
            add(PageContent("", "Uyumlu Burç", "Uyumlu Burç"))
            add(PageContent("", "Uyumsuz Burçlar", "Tarot Kartı"))
            add(PageContent("", "Aynı Burca Sahip Olan Ünlüler", ""))
            add(PageContent("", "Renk", "Renk"))
        }
        return list
    }
    override fun getFragmentView() = R.layout.fragment_profile
}

data class PagerItem(val pageContents: List<PageContent>)
data class PageContent(val imageUrl: String, val title: String, val content: String)