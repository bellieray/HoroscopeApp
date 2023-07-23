package com.eray.horoscopeapp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.appodeal.ads.Appodeal
import com.appodeal.ads.initializing.ApdInitializationCallback
import com.appodeal.ads.initializing.ApdInitializationError
import com.eray.horoscopeapp.ui.SessionViewModel
import com.eray.horoscopeapp.util.DialogUtils
import com.eray.horoscopeapp.util.setStatusBarColor
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment
    private val sessionViewModel by viewModels<SessionViewModel>()
    private val connectivityViewModel by viewModels<ConnectivityViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setStatusBarColor(R.color.app_color)
        setupNavigation()
        connectivityViewModel.checkInternetConnection()
        sessionViewModel.setLoginState()
        sessionViewModel.setUserDetails()
        sessionViewModel.getIsLanguageSelected()
        sessionViewModel.getLanguage()
        initObservers()
        initAppodeal()
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    connectivityViewModel.viewState.collect {
                        it.isConnection?.let { connect ->
                            if (connect.not())
                                DialogUtils.showCustomAlert(
                                    this@MainActivity,
                                    textRes = R.string.please_check_internet_connection
                                )
                        }
                    }
                }
            }
        }
    }

    private fun initAppodeal() {
        Appodeal.initialize(
            this,
            BuildConfig.APP_KEY,
            adTypes = Appodeal.INTERSTITIAL or Appodeal.REWARDED_VIDEO or Appodeal.MREC or Appodeal.NATIVE or Appodeal.INTERSTITIAL or Appodeal.BANNER,
            object : ApdInitializationCallback { override fun onInitializationFinished(errors: List<ApdInitializationError>?) {}
            })
    }

    private fun setupNavigation() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigation.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, _, args ->
            bottomNavigation.isVisible = args?.getBoolean("hideBottomNav") != true
        }
    }
}