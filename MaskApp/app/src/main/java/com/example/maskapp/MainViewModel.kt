package com.example.maskapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.maskapp.model.MaskStoreModel
import com.example.maskapp.model.StoreModel
import com.example.maskapp.network.MaskService
import retrofit2.Call
import java.util.*

class MainViewModel : ViewModel() {
    private val _itemLiveData = MutableLiveData<List<StoreModel>>()
    val itemLiveData: LiveData<List<StoreModel>>
        get() = _itemLiveData

    private val call: Call<MaskStoreModel> =
        MaskService.getInstance().fetchStoreInfo()

    init {
        fetchStoreInfo()
    }

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