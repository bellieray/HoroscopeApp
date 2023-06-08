package com.eray.horoscopeapp.ui.horoscope_detail

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.eray.horoscopeapp.BuildConfig
import com.eray.horoscopeapp.R
import com.eray.horoscopeapp.databinding.FragmentHoroscopeDetailBinding
import com.eray.horoscopeapp.ui.base.BaseFragment
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

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

        binding.icArrowBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun sharePage() {
        // Get the root view of the activity or fragment
        val rootView = requireActivity().window.decorView.rootView

        // Create a Bitmap of the rootView
        rootView.isDrawingCacheEnabled = true
        val screenshotBitmap = Bitmap.createBitmap(rootView.drawingCache)
        rootView.isDrawingCacheEnabled = false

        val screenshotFile = File(requireContext().getExternalFilesDir(null), "screenshot.png")

        try {
            val fileOutputStream = FileOutputStream(screenshotFile)
            screenshotBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
            fileOutputStream.flush()
            fileOutputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        // Show a Toast message with the file path
        Toast.makeText(
            requireContext(),
            "Screenshot saved: ${screenshotFile.absolutePath}",
            Toast.LENGTH_SHORT
        ).show()

        val sendIntent = Intent(Intent.ACTION_SEND)
        sendIntent.type = "image/png"
        sendIntent.putExtra(
            Intent.EXTRA_STREAM,
            FileProvider.getUriForFile(
                requireContext(),
                BuildConfig.APPLICATION_ID + ".provider",
                screenshotFile
            )
        )
        sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        startActivity(Intent.createChooser(sendIntent, "Send Screenshot"))
    }


    override fun getFragmentView() = R.layout.fragment_horoscope_detail
}