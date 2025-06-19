package com.example.metrimonialapp.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.metrimonialapp.data.AppDatabase
import com.example.metrimonialapp.data.User
import com.example.metrimonialapp.data.UserProfileEntity
import com.example.metrimonialapp.network.RetrofitClient
import com.example.metrimonialapp.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.math.abs


class UserViewModel(app: Application) : AndroidViewModel(app) {
    private val dao = AppDatabase.getInstance(app).userDao()
    private val repo = UserRepository(RetrofitClient.apiService, dao)

    val users = repo.usersFlow
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    init {
        viewModelScope.launch(Dispatchers.IO) { repo.fetchAndCacheUsers() }
    }

    fun setSelection(uuid: String, accepted: Boolean) {
        viewModelScope.launch { repo.setSelection(uuid, accepted) }
    }
}
