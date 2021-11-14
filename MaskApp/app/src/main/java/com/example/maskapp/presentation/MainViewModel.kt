package com.example.maskapp.presentation

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.maskapp.model.StoreModel
import com.example.maskapp.network.MaskService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val service: MaskService
    ) : ViewModel() {

    private val _itemLiveData = MutableLiveData<List<StoreModel>>()
    val itemLiveData: LiveData<List<StoreModel>>
        get() = _itemLiveData

    private val _loadingLiveData = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean>
        get() = _loadingLiveData

    private val _location = MutableLiveData<Location>()
    val location: LiveData<Location>
        get() = _location

    private fun setLoading(status: Boolean) {
        _loadingLiveData.value = status
    }

    init {
        _location.value = location.value
    }

    fun fetchStoreInfo() {
        setLoading(true)

        viewModelScope.launch {
            val storeLocation = MaskService.getInstance()
                .fetchStoreInfo(_location.value?.latitude!!, _location.value?.longitude!!)

            val items = storeLocation.stores.filter { items -> items.remain_stat != null }

            _itemLiveData.value = items
            setLoading(false)
        }
    }
}