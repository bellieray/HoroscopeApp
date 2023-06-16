package com.eray.horoscopeapp.ui.horoscope_detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.eray.horoscopeapp.R
import com.eray.horoscopeapp.databinding.FragmentHoroscopeDetailBinding
import com.eray.horoscopeapp.ui.base.BaseFragment
import com.eray.horoscopeapp.util.DialogUtils
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HoroscopeDetailFragment : BaseFragment<FragmentHoroscopeDetailBinding>() {
    private val detailArgs: HoroscopeDetailFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.horoscope = detailArgs.horoscope
        binding.icArrowBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.ivShare.setOnClickListener {
            sharePage()
        }

        binding.tvHoroscopeDescription.setOnClickListener {
            detailArgs.horoscope?.description?.let { it1 ->
                DialogUtils.showMatchingDescriptionTextDetailDialog(
                    requireContext(),
                    it1
                )
            }
        }
    }

    private fun sharePage() {
        val customText =
            "Hey! check out my horoscope for this month about ${detailArgs.horoscope?.name}\n ${detailArgs.horoscope?.description}"
        shareText(customText)

    }

    private fun shareText(text: String) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, text)
        // Set the package name of the desired app

        // You can also set an optional subject for the shared text
        // shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Shared Text Subject");
        startActivity(Intent.createChooser(shareIntent, "Share via"))
    }


    override fun getFragmentView() = R.layout.fragment_horoscope_detail
}