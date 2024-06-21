package com.cleartab.cleartab

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

private const val ARG_LAYOUT_ID = "layout_id"

class Slider_4 : Fragment() {
    private var layoutId: Int? = 3

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        arguments?.let {
            layoutId = it.getInt(ARG_LAYOUT_ID)
            Log.d("PageFragment", "Creating fragment with layout ID: $layoutId")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId!!, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(layoutId: Int) = Slider_4().apply {
            arguments = Bundle().apply {
                putInt(ARG_LAYOUT_ID, layoutId)
            }
        }
    }
}