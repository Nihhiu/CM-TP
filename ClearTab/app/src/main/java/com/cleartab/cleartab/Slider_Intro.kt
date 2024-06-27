package com.cleartab.cleartab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

// import android.app.src.main.res.layout.fragment_slider__intro.*

class Slider_Intro : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.slider__intro, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle("")
    }

    fun setTitle(title: String) {
        val textView = view?.findViewById<TextView>(R.id.slider_text)
        if (textView != null) {
            textView.text = title
        }
    }
}
