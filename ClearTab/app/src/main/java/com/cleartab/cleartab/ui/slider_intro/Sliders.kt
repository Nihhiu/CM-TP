package com.cleartab.cleartab.ui.slider_intro

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.cleartab.cleartab.R
import com.cleartab.cleartab.ui.login.login

class Sliders : AppCompatActivity() {
    private var currentSlide = 1 // Começa com o primeiro slide

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.slider_intro_1)

        // Configurar o botãos
        val btnNext: ImageButton = findViewById(R.id.imageButton)

        btnNext.setOnClickListener {
            // Avançar para o próximo slide
            currentSlide++
            showCurrentSlide()
        }
    }

    private fun showCurrentSlide() {
        when (currentSlide) {
            2 -> {
                setContentView(R.layout.slider_intro_2)
                // Configurar o botão novamente após trocar a visualização
                val btnNext: ImageButton = findViewById(R.id.imageButton)

                btnNext.setOnClickListener {
                    // Avançar para o próximo slide
                    currentSlide++
                    showCurrentSlide()
                }
            }
            3 -> {
                setContentView(R.layout.slider_intro_3)
                val btnNext: ImageButton = findViewById(R.id.imageButton)

                btnNext.setOnClickListener {
                    // Avançar para o próximo slide
                    currentSlide++
                    showCurrentSlide()
                }
            }
            4 -> {
                setContentView(R.layout.slider_intro_4)
                val btnNext: ImageButton = findViewById(R.id.imageButton3)

                btnNext.setOnClickListener {
                    // Avançar para o próximo slide
                    currentSlide++
                    showCurrentSlide()
                }
            }
            else -> {
                val intent = Intent(this, login::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}