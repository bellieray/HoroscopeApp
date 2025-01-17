package com.eray.horoscopeapp.ui.calculatesign

import androidx.navigation.fragment.findNavController
import com.eray.horoscopeapp.R
import com.eray.horoscopeapp.ui.base.BaseCalculateSignFragment
import com.eray.horoscopeapp.util.*
import dagger.hilt.android.AndroidEntryPoint

private const val SUN_SIGN_PAGE_OPENED = "sun_sign_page_opened"
@AndroidEntryPoint
class CalculateSunSignFragment : BaseCalculateSignFragment() {
    override fun onPageOpened() {
       calculateSignViewModel.sendEvent(SUN_SIGN_PAGE_OPENED)
    }

    override fun doOnCalculateClicked() {
        val sunSignId = StringUtils.calculateSunSign(
            binding.etBirthdateUserBirthday.text.toString(),
            binding.etUserBirthTime.text.toString()
        )
        sunSignId?.let { calculateSignViewModel.filterHoroscopeListById(it) }
        DialogUtils.showResultDialog(
            requireActivity(),
            iconUrl = calculateSignViewModel.viewState.value.userHoroscope?.imageUrl,
            title = calculateSignViewModel.viewState.value.userHoroscope?.name,
            message = null,
            buttonText = requireContext().getString(R.string.show_sign_detail)
        ) {
            findNavController().navigateWithPushAnimation(
                CalculateSunSignFragmentDirections.actionCalculateSunSignFragmentToHoroscopeDetailFragment(
                    calculateSignViewModel.viewState.value.userHoroscope
                )
            )
        }
    }

    override fun setPageTitle(): String = getString(R.string.sun_sign)
}