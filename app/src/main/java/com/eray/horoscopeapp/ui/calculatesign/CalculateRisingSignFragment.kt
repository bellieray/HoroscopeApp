package com.eray.horoscopeapp.ui.calculatesign

import androidx.navigation.fragment.findNavController
import com.eray.horoscopeapp.R
import com.eray.horoscopeapp.ui.base.BaseCalculateSignFragment
import com.eray.horoscopeapp.util.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CalculateRisingSignFragment : BaseCalculateSignFragment() {

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
                    findNavController().navigate(
                        CalculateRisingSignFragmentDirections.actionCalculateRisingSignFragmentToHoroscopeDetailFragment(
                            calculateSignViewModel.viewState.value.userHoroscope
                        )
                    )
                }
            }
    }
}