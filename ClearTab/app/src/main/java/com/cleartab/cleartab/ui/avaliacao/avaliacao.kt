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
    private lateinit var adesempenho: RatingBar
    private lateinit var atrabalho: RatingBar
    private lateinit var acritica: EditText
    private lateinit var aconfirmar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.avaliacao)

        adesempenho = findViewById(R.id.a_desempenho)
        atrabalho = findViewById(R.id.a_trabalho)
        acritica = findViewById(R.id.a_critica)
        aconfirmar = findViewById(R.id.a_confirmar)
        //TODO
    }
}