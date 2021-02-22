package com.example.maskapp.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.maskapp.enqueueListener
import com.example.maskapp.model.MaskStoreModel
import com.example.maskapp.model.StoreModel
import com.example.maskapp.network.MaskService
import retrofit2.Call
import java.util.*

class MainViewModel : ViewModel() {
    private val _itemLiveData = MutableLiveData<List<StoreModel>>()
    val itemLiveData: LiveData<List<StoreModel>>
        get() = _itemLiveData

    private val _latitude = MutableLiveData<Double>()
    val latitude: LiveData<Double>
        get() = _latitude

    private val _longitude = MutableLiveData<Double>()
    val longitude: LiveData<Double>
        get() = _longitude

    fun setLatitude(lat : Double) {
        _latitude.value = lat
    }

    fun setLongitude(lng: Double) {
        _longitude.value = lng
    }

//    private val _location = MutableLiveData<LocationModel>()
//    val location: LiveData<LocationModel>
//        get() = _location

    private val call: Call<MaskStoreModel> =
        MaskService.getInstance().fetchStoreInfo(_latitude.value!!, _longitude.value!!)

    fun fetchStoreInfo() {
            call.clone().enqueueListener(
                onSuccess = {
                    val items: List<StoreModel> = it.body()!!.stores
                        .filter { items -> items.remain_stat != null }

                    _itemLiveData.postValue(items)
                },
                onError = {
                    Log.d(TAG_ERROR, it.message())
                    _itemLiveData.postValue(Collections.emptyList())
                }
            )
        }

    companion object {
        const val TAG_ERROR = "Error"
    }
}