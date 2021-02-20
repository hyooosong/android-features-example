package com.example.maskapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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
        val mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel

        maskAdapter = MaskAdapter()
        binding.rcvMaskStore.apply {
            adapter=maskAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        mainViewModel.itemLiveData.observe(this, Observer {
            maskAdapter.refreshData(it)
            supportActionBar!!.title = "마스크 재고 있는 곳 : ${it.size}곳"
        })
    }
}