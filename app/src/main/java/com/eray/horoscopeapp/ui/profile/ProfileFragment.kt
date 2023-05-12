package com.eray.horoscopeapp.ui.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.eray.horoscopeapp.R
import com.eray.horoscopeapp.databinding.FragmentProfileBinding
import com.eray.horoscopeapp.ui.SessionViewModel
import com.eray.horoscopeapp.ui.base.BaseFragment
import com.eray.horoscopeapp.ui.profile.adapter.ProfileAdapter
import com.eray.horoscopeapp.util.checkHoroscope
import java.util.*

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {
    private val sessionViewModel by activityViewModels<SessionViewModel>()
    private val adapter = ProfileAdapter()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
    }

    private fun initViews() {
        binding.vpProfile.adapter = this.adapter
        adapter.submitList(preparePagerList())
        binding.diProfile.attachTo(binding.vpProfile)
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            sessionViewModel.viewState.collect {
                binding.tvUsername.text = it.personalDetail?.name?.capitalize()
                binding.tvUserHoroscope.text = it.personalDetail?.birthTime?.checkHoroscope()?.first
                it.personalDetail?.birthTime?.checkHoroscope()?.second?.let { it1 ->
                    binding.ivUserHoroscope.setBackgroundResource(
                        it1
                    )
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