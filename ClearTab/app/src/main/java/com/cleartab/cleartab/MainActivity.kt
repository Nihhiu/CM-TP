package com.cleartab.cleartab

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.cleartab.cleartab.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
//    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        //binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(R.layout.activity_main)
//
//        val viewPager = findViewById<ViewPager2>(R.id.viewPager)
//        viewPager.adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
//        viewPager = findViewById(R.id.viewPager)
//        tabLayout = findViewById(R.id.tabLayout)
//
//        val layoutIds = listOf(
//            R.layout.fragment_slider_1,
//            R.layout.fragment_slider_2,
//            R.layout.fragment_slider_3,
//            R.layout.fragment_slider_4
//        )
//        viewPager.adapter = ViewPagerAdapter(this, layoutIds)
//
//        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
//            tab.text = "Page ${(position + 1)}"
//        }.attach()


    lateinit var viewPager: ViewPager2
    lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager = findViewById(R.id.viewPager)
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, lifecycle)

        // Add your fragments here
        viewPagerAdapter.addFragment(Slider_1(), "First Fragment")
        viewPagerAdapter.addFragment(Slider_2(), "Second Fragment")
        viewPagerAdapter.addFragment(Slider_3(), "Third Fragment")
        viewPagerAdapter.addFragment(Slider_4(), "Fourth Fragment")

        viewPager.adapter = viewPagerAdapter
    }
}
