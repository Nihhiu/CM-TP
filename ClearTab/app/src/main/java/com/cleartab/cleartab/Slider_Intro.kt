package com.cleartab.cleartab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
// import android.app.src.main.res.layout.fragment_slider__intro.*

class Slider_Intro : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_slider__intro, container, false)
    }

    fun setTitle(title: String){
        slider_text.text = title;
    }
}