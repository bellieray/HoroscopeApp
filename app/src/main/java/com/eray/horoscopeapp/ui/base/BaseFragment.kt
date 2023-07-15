package com.eray.horoscopeapp.ui.base

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import coil.network.HttpException
import com.appodeal.ads.Appodeal
import com.eray.horoscopeapp.R
import com.eray.horoscopeapp.util.DialogUtils
import java.io.IOException
import java.util.concurrent.TimeoutException

abstract class BaseFragment<VDB : ViewDataBinding> : Fragment() {

    private var _binding: VDB? = null
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, getFragmentView(), container, false)
        return binding.root
    }

    fun handleError(exception: Exception, activity: Activity) {
        when (exception) {
            is HttpException -> DialogUtils.showCustomAlert(
                activity, errorText = exception.localizedMessage.toString()
            )

            is IOException -> {
                DialogUtils.showCustomAlert(
                    activity,
                    R.string.please_check_internet_connection
                )
            }

            is TimeoutException -> {
                DialogUtils.showCustomAlert(
                    activity,
                    R.string.request_time_out
                )
            }
        }
    }

    protected fun showInterstitialAd() {
        Appodeal.setTesting(true)
        if (Appodeal.isLoaded(Appodeal.INTERSTITIAL)) {
            Appodeal.show(requireActivity(), Appodeal.INTERSTITIAL)
        }
    }

    protected fun showMrecAd(id: Int) {
        Appodeal.setTesting(true)
        Appodeal.setMrecViewId(id)
        if (Appodeal.canShow(Appodeal.MREC, "default")) {
            Appodeal.show(requireActivity(), Appodeal.MREC, "default")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    abstract fun getFragmentView(): Int
}