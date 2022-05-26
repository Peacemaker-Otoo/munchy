package com.android.muncher.ui.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.android.muncher.data.repository.BaseRepository

abstract class BaseViewModel(
    private val repository: BaseRepository
) : ViewModel() {
    suspend fun logout() = withContext(Dispatchers.IO) { repository.logout() }

}