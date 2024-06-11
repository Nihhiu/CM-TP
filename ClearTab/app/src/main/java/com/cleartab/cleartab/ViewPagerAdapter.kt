package com.cleartab.cleartab

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragmentActivity: FragmentActivity, private val layoutIds: List<Int>) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = layoutIds.size

    override fun createFragment(position: Int): Fragment {
        return Slider_1.newInstance(layoutIds[position])
    }
}