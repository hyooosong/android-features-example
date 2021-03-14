package com.example.hilt_di.ui.main

import androidx.lifecycle.ViewModel
import com.example.hilt_di.ui.data.MyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MyRepository
): ViewModel() {
    fun getRepositoryHash() = repository.toString()
}