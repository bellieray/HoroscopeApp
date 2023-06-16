package com.eray.horoscopeapp.ui.splash

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.eray.horoscopeapp.R
import com.eray.horoscopeapp.databinding.FragmentSplashBinding
import com.eray.horoscopeapp.ui.SessionViewModel
import com.eray.horoscopeapp.ui.base.BaseFragment
import com.eray.horoscopeapp.util.navigateWithPushAnimationAndPop


class SplashFragment : BaseFragment<FragmentSplashBinding>() {
    private lateinit var countDownTimer: CountDownTimer
    private val sessionViewModel by activityViewModels<SessionViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        splashScreen()
    }

    private fun splashScreen() {
        countDownTimer = object : CountDownTimer(3000, 1000) {
            override fun onTick(p0: Long) {}
            override fun onFinish() {
                viewLifecycleOwner.lifecycleScope.launchWhenResumed {
                    sessionViewModel.viewState.collect {
                        if (it.isLoggedIn == true) findNavController().navigateWithPushAnimationAndPop(
                            SplashFragmentDirections.actionSplashFragmentToHomeFragment(),
                            R.id.splashFragment,
                            isInclusive = true
                        )
                        else findNavController().navigateWithPushAnimationAndPop(
                            SplashFragmentDirections.toLoginFragment(),
                            R.id.splashFragment,
                            isInclusive = true
                        )
                    }
                }
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