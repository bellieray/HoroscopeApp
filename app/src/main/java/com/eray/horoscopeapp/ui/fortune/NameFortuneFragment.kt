package com.eray.horoscopeapp.ui.fortune

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.os.Bundle
import android.view.View
import androidx.core.animation.doOnEnd
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.eray.horoscopeapp.R
import com.eray.horoscopeapp.databinding.FragmentNameFortuneBinding
import com.eray.horoscopeapp.ui.base.BaseFragment
import com.eray.horoscopeapp.util.*
import com.eray.horoscopeapp.util.StringUtils.calculateNameMatch
import com.eray.horoscopeapp.util.StringUtils.calculateNameNumber
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.firebase.firestore.QuerySnapshot
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NameFortuneFragment : BaseFragment<FragmentNameFortuneBinding>(), AnimatorUpdateListener {

    private val nameFortuneViewModel: NameFortuneViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            clMatchNameFortuneContainer.isVisible =
                tlNameFortune.selectedTabPosition == NameFortune.MATCH.ordinal
            tlNameFortune.addOnTabSelectedListener(object : OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    fortuneResult = null
                    DeviceUtils.closeKeyboard(requireActivity(), requireView())
                    etNameFortune.text = null
                    etMatchNameFortune.text = null
                    val ws: Float = llNameFortuneContainer.weightSum
                    var anim = ObjectAnimator()
                    when (tab?.position) {
                        NameFortune.OWN.ordinal -> {
                            anim = ObjectAnimator.ofFloat(
                                llNameFortuneContainer,
                                "weightSum",
                                ws,
                                1.0f
                            )
                            anim.doOnEnd {
                                clMatchNameFortuneContainer.visibility = View.GONE
                            }
                        }
                        NameFortune.MATCH.ordinal -> {
                            clMatchNameFortuneContainer.visibility = View.VISIBLE
                            anim = ObjectAnimator.ofFloat(
                                llNameFortuneContainer,
                                "weightSum",
                                ws,
                                2.0f
                            )
                        }
                    }
                    anim.duration = 1000
                    anim.addUpdateListener(this@NameFortuneFragment)
                    anim.start()
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {}
                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })
            btnNameFortuneCalculate.setOnClickListener {
                if (StringUtils.checkInformation(etNameFortune.text.toString())) {
                    when (tlNameFortune.selectedTabPosition) {
                        NameFortune.OWN.ordinal -> {
                            nameFortuneViewModel.getNameFortuneResult(
                                calculateNameNumber(
                                    etNameFortune.text.toString(),
                                    "tr"
                                )
                            )
                        }
                        NameFortune.MATCH.ordinal -> {
                            if (StringUtils.checkInformation(etMatchNameFortune.text.toString())) {
                                fortuneResult = "%" + calculateNameMatch(
                                    etNameFortune.text.toString(),
                                    etMatchNameFortune.text.toString()
                                )
                                DialogUtils.showResultDialog(
                                    activity = requireActivity(),
                                    iconUrl = null,
                                    title = requireContext().getString(R.string.result),
                                    message = "%" + calculateNameMatch(
                                        etNameFortune.text.toString(),
                                        etMatchNameFortune.text.toString()
                                    ),
                                    buttonText = null, buttonClick = null
                                )
                            } else {
                                DialogUtils.showCustomAlert(
                                    requireActivity(),
                                    textRes = R.string.please_fill_the_all_blanks
                                )
                            }
                        }
                    }
                    DeviceUtils.closeKeyboard(requireActivity(), requireView())
                } else {
                    DialogUtils.showCustomAlert(
                        requireActivity(),
                        R.string.please_fill_the_all_blanks
                    )
                }
            }
            executePendingBindings()
        }
        observe()
    }

    private fun observe() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                nameFortuneViewModel.viewState.collect { viewState ->
                    viewState.nameFortuneViewEvents?.firstOrNull()?.let { event ->
                        when (event) {
                            NameFortuneViewEvent.ShowResult -> {
                                viewState.nameFortuneResult?.let {
                                    DialogUtils.showResultDialog(
                                        activity = requireActivity(),
                                        iconUrl = null,
                                        title = it.id,
                                        message = it.description,
                                        buttonText = null, buttonClick = null
                                    )
                                }
                            }
                        }
                        nameFortuneViewModel.eventConsumed(event)
                    }
                }
            }
        }
    }

    override fun getFragmentView(): Int = R.layout.fragment_name_fortune
    override fun onAnimationUpdate(p0: ValueAnimator?) {
        binding.llNameFortuneContainer.requestLayout()
    }
}

enum class NameFortune {
    OWN, MATCH
}

data class NameFortuneItem(
    val id: String?,
    val description: String?
) {
    companion object {
        fun toNameFortuneItemList(qs: QuerySnapshot?) =
            qs?.documents?.map {
                NameFortuneItem(
                    id = it.get("id") as? String,
                    description = it.get("description") as? String
                )
            }
    }
}