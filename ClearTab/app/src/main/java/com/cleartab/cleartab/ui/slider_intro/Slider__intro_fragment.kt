package com.cleartab.cleartab.ui.slider_intro

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.navigation.fragment.findNavController
import com.cleartab.cleartab.R
import com.cleartab.cleartab.ui.slider_intro.*

class Slider__intro_fragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.slider__intro, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageButton: ImageButton = view.findViewById(R.id.imageButton)

        imageButton.setOnClickListener {
            findNavController().navigate(R.id.action_slider__intro_to_slider_intro_2)
        }
    }
}