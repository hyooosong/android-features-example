package com.example.hilt_di.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.hilt_di.ui.data.MyRepository

class MainViewModel @ViewModelInject constructor(
    private val repository: MyRepository
): ViewModel() {
    fun getRepositoryHash() = repository.toString()
}