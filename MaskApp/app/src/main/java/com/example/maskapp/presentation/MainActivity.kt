package com.example.maskapp.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.maskapp.R
import com.example.maskapp.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var maskAdapter: MaskAdapter
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel

        setRecyclerview(binding.rcvMaskStore)
        setRCVData()

        isVisibleLoading()
        locationPermission()
    }

    private fun setRecyclerview(view: RecyclerView) {
        maskAdapter = MaskAdapter()
        view.apply {
            adapter = maskAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun setRCVData() {
        mainViewModel.itemLiveData.observe(this@MainActivity, Observer { store ->
            maskAdapter.refreshData(store)
            supportActionBar!!.title = "마스크 재고 있는 곳 : ${store.size}곳"
        })
    }

    private fun isVisibleLoading() {
        mainViewModel.loadingLiveData.observe(this@MainActivity, Observer { isLoading ->
            binding.progressBar.visibility =
                if (isLoading) View.VISIBLE else View.GONE
        })
    }

    private fun locationPermission() {
        val requestPermission = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { granted ->
            if (granted)
                getLocation()
        }
        requestPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        fusedLocationClient.lastLocation
            .addOnSuccessListener { currentLocation: Location? ->
                if (currentLocation != null) {
                    //위도, 경도 얻는 부분
                    currentLocation.latitude = 37.188078
                    currentLocation.longitude = 127.043002

                    mainViewModel.apply {
                        location.value!!.latitude = currentLocation.latitude
                        location.value!!.longitude = currentLocation.longitude
                    }

                    mainViewModel.fetchStoreInfo()
                }
            }
    }
}