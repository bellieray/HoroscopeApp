package com.eray.horoscopeapp.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

object ConnectionUtils {
    fun checkInternetConnection(context: Context): Boolean {
        // Get the ConnectivityManager
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // Get all networks
        val networks = connectivityManager.allNetworks

        // Check if any network has internet access
        for (network in networks) {
            val networkCapabilities =
                connectivityManager.getNetworkCapabilities(network) ?: continue

            if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) || networkCapabilities.hasCapability(
                    NetworkCapabilities.NET_CAPABILITY_SUPL
                )
            ) {
                return true
            }
        }
        return false
    }
}