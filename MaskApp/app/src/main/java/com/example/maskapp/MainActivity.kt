package com.example.maskapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.maskapp.databinding.ActivityMainBinding
import com.example.maskapp.model.MaskStoreModel
import com.example.maskapp.model.StoreModel
import com.example.maskapp.network.MaskService
import retrofit2.Call

class MainActivity : AppCompatActivity() {
    private lateinit var maskAdapter: MaskAdapter
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        maskAdapter = MaskAdapter()
        connectServer()

        binding.rcvMaskStore.apply {
            adapter=maskAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        binding.btnRefresh.setOnClickListener {
            connectServer()
        }
    }

    private fun connectServer(){
        val call: Call<MaskStoreModel> =
            MaskService.getInstance().fetchStoreInfo()
        call.enqueueListener(
            onSuccess = {
                val items : List<StoreModel> = it.body()!!.stores
                maskAdapter.refreshData(items
                    .filter { items -> items.remain_stat != null }
                )
                supportActionBar!!.title = "마스크 재고 있는 곳 : ${items.size}곳"
            },
            onError = {
                Log.d(TAG_ERROR, it.message())
            }
        )
    }

    companion object {
        const val TAG_ERROR = "Error"
    }
}