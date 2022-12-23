package com.eray.horoscopeapp.ui.splash

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.navigation.fragment.findNavController
import com.eray.horoscopeapp.R
import com.eray.horoscopeapp.databinding.FragmentSplashBinding
import com.eray.horoscopeapp.ui.base.BaseFragment


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