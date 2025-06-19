package com.example.metrimonialapp.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext

@Composable
fun connectivityState(): State<Boolean> {
    val context = LocalContext.current
    val cm = remember {
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }
    val isOnline = remember { mutableStateOf(false) }

    DisposableEffect(cm) {
        val cap = cm.getNetworkCapabilities(cm.activeNetwork)
        isOnline.value = cap?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true

        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                isOnline.value = true
            }
            override fun onLost(network: Network) {
                isOnline.value = false
            }
        }
        cm.registerDefaultNetworkCallback(callback)
        onDispose { cm.unregisterNetworkCallback(callback) }
    }

    return isOnline
}
