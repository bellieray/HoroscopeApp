package com.eray.horoscopeapp

import android.content.Context
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
import com.eray.horoscopeapp.ui.SessionViewModel
import com.eray.horoscopeapp.util.DialogUtils
import com.eray.horoscopeapp.util.setStatusBarColor
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*

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
        sessionViewModel.setLanguage()
        sessionViewModel.setAppRecreatedFlag()
        initObservers()
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    sessionViewModel.viewState.collect {
                        if (it.isEnglish == true) {
                            updateResources(this@MainActivity, "en")
                        } else {
                            if (it.isEnglish == false) updateResources(this@MainActivity, "tr")
                        }

                    }
                }

                launch {
                    connectivityViewModel.viewState.collect {
                        it.isConnection?.let {connect ->
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

    private fun updateResources(context: Context, language: String) {
        val locale = Locale(language)
        resources.configuration.setLocale(locale)
        resources.updateConfiguration(
            resources.configuration,
            resources.displayMetrics
        )
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