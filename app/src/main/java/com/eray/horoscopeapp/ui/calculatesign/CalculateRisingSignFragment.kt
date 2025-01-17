package com.eray.horoscopeapp.ui.calculatesign

import androidx.navigation.fragment.findNavController
import com.eray.horoscopeapp.R
import com.eray.horoscopeapp.ui.base.BaseCalculateSignFragment
import com.eray.horoscopeapp.util.*
import dagger.hilt.android.AndroidEntryPoint

private const val RISING_SIGN_PAGE_OPENED = "rising_sign_page_opened"
@AndroidEntryPoint
class CalculateRisingSignFragment : BaseCalculateSignFragment() {
    override fun onPageOpened() {
        calculateSignViewModel.sendEvent(RISING_SIGN_PAGE_OPENED)
    }

    override fun doOnCalculateClicked() {
        val birthTime = binding.etUserBirthTime.text.toString()
        calculateSignViewModel.convertDateToHoroscope()
        horoscopeList.find { it.id?.toInt() == calculateSignViewModel.viewState.value.userHoroscopeId }
            ?.let {
                val horoscopeId = birthTime.checkRisingHoroscope(it)
                horoscopeId?.let { id -> calculateSignViewModel.filterHoroscopeListById(id) }
                DialogUtils.showResultDialog(
                    requireActivity(),
                    iconUrl = calculateSignViewModel.viewState.value.userHoroscope?.imageUrl,
                    title = calculateSignViewModel.viewState.value.userHoroscope?.name,
                    message = null,
                    buttonText = requireContext().getString(R.string.show_sign_detail)
                ) {
                    findNavController().navigateWithPushAnimation(
                        CalculateRisingSignFragmentDirections.actionCalculateRisingSignFragmentToHoroscopeDetailFragment(
                            calculateSignViewModel.viewState.value.userHoroscope
                        )
                    )
                }
            }
    }

    override fun setPageTitle(): String = getString(R.string.rising_sign)
}