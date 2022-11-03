package com.mami.horoscopeapp.ui.splash

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.mami.horoscopeapp.R
import com.mami.horoscopeapp.databinding.FragmentSplashBinding
import com.mami.horoscopeapp.ui.base.BaseFragment
import com.mami.horoscopeapp.util.navigateWithPushAnimation


class SplashFragment : BaseFragment<FragmentSplashBinding>() {
    private lateinit var countDownTimer: CountDownTimer

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        splashScreen()
    }

    private fun splashScreen() {

        countDownTimer = object : CountDownTimer(3000, 1000) {
            override fun onTick(p0: Long) {}
            override fun onFinish() {
                findNavController().navigate(R.id.action_splashFragment_to_dateTimeFragment)
            }
        }.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (this::countDownTimer.isInitialized)
            countDownTimer.cancel()
    }

    override fun getFragmentView(): Int = R.layout.fragment_splash
}