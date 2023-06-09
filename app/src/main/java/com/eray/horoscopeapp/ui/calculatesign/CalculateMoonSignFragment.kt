package com.eray.horoscopeapp.ui.calculatesign

import androidx.navigation.fragment.findNavController
import com.eray.horoscopeapp.R
import com.eray.horoscopeapp.ui.base.BaseCalculateSignFragment
import com.eray.horoscopeapp.util.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CalculateMoonSignFragment : BaseCalculateSignFragment() {

    override fun doOnCalculateClicked() {
        val moonSignId = StringUtils.calculateMoonSign(
            binding.etBirthdateUserBirthday.text.toString(),
            binding.etUserBirthTime.text.toString()
        )
        calculateSignViewModel.filterHoroscopeListById(moonSignId)
        DialogUtils.showResultDialog(
            requireActivity(),
            iconUrl = calculateSignViewModel.viewState.value.userHoroscope?.imageUrl,
            title = calculateSignViewModel.viewState.value.userHoroscope?.name,
            message = null,
            buttonText = requireContext().getString(R.string.show_sign_detail)
        ) {
            findNavController().navigate(
                CalculateMoonSignFragmentDirections.actionCalculateMoonSignFragmentToHoroscopeDetailFragment(
                    calculateSignViewModel.viewState.value.userHoroscope
                )
            )
        }
    }
}