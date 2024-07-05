package com.cleartab.cleartab.ui.avaliacao

import android.media.Rating
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.launch
import androidx.lifecycle.lifecycleScope
import android.widget.Button
import android.widget.RatingBar
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import com.cleartab.cleartab.R
import com.cleartab.cleartab.retrofit.SupabaseService
import com.cleartab.cleartab.utils.SharedPreferencesUtil

class avaliacao: AppCompatActivity() {
    private lateinit var desempenho: RatingBar
    private lateinit var comunicacao: RatingBar
    private lateinit var trabalho: RatingBar
    private lateinit var critica: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.avaliacao)

        desempenho = findViewById(R.id.rating)
        comunicacao = findViewById(R.id.rating2)
        trabalho = findViewById(R.id.rating3)
        critica = findViewById(R.id.)
        //TODO
    }
}