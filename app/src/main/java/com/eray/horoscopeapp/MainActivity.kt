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
import com.eray.horoscopeapp.ui.SessionViewModel
import com.eray.horoscopeapp.util.ConnectionUtils
import com.eray.horoscopeapp.util.DialogUtils
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment
    private val sessionViewModel by viewModels<SessionViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNavigation()
        sessionViewModel.setLoginState()
        sessionViewModel.setUserDetails()
        if (ConnectionUtils.checkInternetConnection(this).not()) {
            sessionViewModel.setConnectionError(true)
        }
        initObservers()
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                sessionViewModel.viewState.collect {
                    if (it.isThereConnectionError) {
                        DialogUtils.showCustomAlert(this@MainActivity, textRes = R.string.please_check_internet_connection)
                    }
                }
            }
        }
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