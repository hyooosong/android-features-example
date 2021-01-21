package com.example.androidpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.example.androidpractice.Calendar.CalendarFragment
import com.example.androidpractice.Home.HomeFragment
import com.example.androidpractice.Mypage.MypageFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {
    private lateinit var viewpagerAdapter : MainViewPagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setViewpager()
        setBottomNavigation()
        slideView()
    }

    fun setViewpager() {
        viewpagerAdapter = MainViewPagerAdapter(supportFragmentManager)
        viewpagerAdapter.fragments = listOf(
            CalendarFragment(),
            HomeFragment(),
            MypageFragment()
        )
        viewpager_main.adapter = viewpagerAdapter
    }

    fun setBottomNavigation() {
        bottom_navigation.setOnNavigationItemSelectedListener {
            var index by Delegates.notNull<Int>()
            when(it.itemId) {
                R.id.calendar -> index = 0
                R.id.home -> index = 1
                R.id.mypage -> index = 2
            }
            viewpager_main.currentItem = index
            true
        }
    }

    fun slideView() {
        viewpager_main.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {  }

            override fun onPageSelected(position: Int) {
                bottom_navigation.menu.getItem(position).isChecked = true
            }

            override fun onPageScrollStateChanged(state: Int) {  }
        })
    }
}