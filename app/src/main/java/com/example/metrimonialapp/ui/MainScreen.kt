package com.example.metrimonialapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.metrimonialapp.viewmodel.UserViewModel

@Composable
fun MainScreen(viewModel: UserViewModel) {
    val isOnline by connectivityState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(isOnline) {
        val msg = if (isOnline) "You are online" else "You are offline"
        snackbarHostState.showSnackbar(msg)
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            ProfileMatchesScreen(viewModel)
        }
    }
}
