package com.example.bottomsheet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bottomsheet.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.activity = this
        binding.lifecycleOwner = this
    }

    fun openDialog() {
        val bottomSheetDialogFragment = BottomSheetFragment()
        bottomSheetDialogFragment.show(supportFragmentManager, "TAG")
    }
}