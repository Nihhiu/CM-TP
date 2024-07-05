package com.cleartab.cleartab.ui.projetos

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.cleartab.cleartab.R
import com.cleartab.cleartab.retrofit.SupabaseService
import com.cleartab.cleartab.retrofit.tables.Projeto
import com.cleartab.cleartab.ui.criar_projeto.criar_projeto
import com.cleartab.cleartab.ui.home.home
import com.cleartab.cleartab.utils.SharedPreferencesUtil
import kotlinx.coroutines.launch

class projetos: AppCompatActivity {
    private lateinit var precycler: RecyclerView
    private lateinit var padicionar: Button
    private val db = SupabaseService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.projetos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.projetos)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        precycler = findViewById(R.id.recycler)
        padicionar = findViewById(R.id.button2)



        padicionar.setOnClickListener {
            val intent = Intent(this, criar_projeto::class.java)
            startActivity(intent)
        }
    }
}