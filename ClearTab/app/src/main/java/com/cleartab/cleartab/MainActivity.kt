package com.cleartab.cleartab

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.cleartab.cleartab.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        viewPager = findViewById(R.id.viewPager)
        tabLayout = findViewById(R.id.tabLayout)

        val layoutIds = listOf(
            R.layout.fragment_slider_1,
            R.layout.fragment_slider_2,
            R.layout.fragment_slider_3,
            R.layout.fragment_slider_4
        )
        viewPager.adapter = ViewPagerAdapter(this, layoutIds)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = "Page ${(position + 1)}"
        }.attach()
    }
}