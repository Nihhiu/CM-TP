package com.cleartab.cleartab

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cleartab.cleartab.ui.login.login
import com.cleartab.cleartab.ui.slider_intro.Sliders

class InicioSliders: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences: SharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE)
        val isFirstRun = sharedPreferences.getBoolean("isFirstRun", true)

        if (isFirstRun) {
            // Primeiro uso, mostrar sliders
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putBoolean("isFirstRun", false)
            editor.apply()

            val intent = Intent(this, Sliders::class.java)
            startActivity(intent)
            finish()
        } else {
            val intent = Intent(this, login::class.java)
            startActivity(intent)
            finish()
        }
    }
}