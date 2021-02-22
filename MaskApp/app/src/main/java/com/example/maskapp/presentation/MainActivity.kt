package com.example.maskapp.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.maskapp.R
import com.example.maskapp.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission


class MainActivity : AppCompatActivity() {
    private lateinit var maskAdapter: MaskAdapter
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel

        binding.rcvMaskStore.apply {
            maskAdapter = MaskAdapter()
            adapter = maskAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        mainViewModel.itemLiveData.observe(this, Observer {
            maskAdapter.refreshData(it)
            supportActionBar!!.title = "마스크 재고 있는 곳 : ${it.size}곳"
        })

        setLocationPermission()
        getLocation()
    }


    private fun setLocationPermission() {
        val permissionlistener: PermissionListener = object : PermissionListener {
            override fun onPermissionGranted() {

            }

            override fun onPermissionDenied(deniedPermissions: List<String>) {
                Toast.makeText(
                    this@MainActivity,
                    "Permission Denied\n$deniedPermissions",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        TedPermission.with(this)
            .setPermissionListener(permissionlistener)
            .setDeniedMessage("권한을 승인하지 않으면, 앱을 사용할 수 없어요! 설정에서 변경해주세요.")
            .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
            .check()
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    //위도, 경도 얻는 부분
                    location.latitude = 37.188078
                    location.longitude = 127.043002
                    mainViewModel.apply {
                        latitude.observe(this@MainActivity, Observer {
                            setLatitude(location.latitude)
                        })
                        longitude.observe(this@MainActivity, Observer {
                            setLongitude(location.longitude)
                        })
                        fetchStoreInfo()
                    }
                }
            }
    }
}