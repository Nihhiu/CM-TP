package com.cleartab.cleartab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
// import android.app.src.main.res.layout.fragment_slider__intro.*

private const val ARG_LAYOUT_ID = "layout_id"

class Slider_1 : Fragment() {
    private var layoutId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        arguments?.let {
            layoutId = it.getInt(ARG_LAYOUT_ID)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId!!, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(layoutId: Int) = Slider_1().apply {
            putInt(ARG_LAYOUT_ID, layoutId)
        }
    }

    fun setTitle(title: String){
        slider_text.text = title;
    }
}