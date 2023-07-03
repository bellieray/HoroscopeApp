package com.eray.horoscopeapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ConnectivityViewModel @Inject constructor(val application: App) :
    ViewModel() {
    private val _viewState = MutableStateFlow(ConnectivityViewState())
    val viewState = _viewState.asStateFlow()
    private val connectivityBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            updateConnectivityState()
        }
    }
    init {
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        application.registerReceiver(connectivityBroadcastReceiver, intentFilter)
    }

    private val connectivityManager by lazy {
        application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }


    private fun updateConnectivityState() {
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        val isConnected = activeNetworkInfo?.isConnectedOrConnecting ?: false
        _viewState.value = ConnectivityViewState(isConnection = isConnected)
    }

    val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            _viewState.update {
                it.copy(isConnection = true)
            }
        }

        override fun onLost(network: Network) {
            _viewState.update {
                it.copy(isConnection = false)
            }
        }

    }

    fun checkInternetConnection() {
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }


    override fun onCleared() {
        super.onCleared()
        connectivityManager.unregisterNetworkCallback(networkCallback)
        application.unregisterReceiver(connectivityBroadcastReceiver)
    }

}

data class ConnectivityViewState(val isConnection: Boolean? = null)