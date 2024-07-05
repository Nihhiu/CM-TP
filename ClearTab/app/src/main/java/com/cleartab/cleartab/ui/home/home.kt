package com.cleartab.cleartab.ui.home

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cleartab.cleartab.R
import com.cleartab.cleartab.retrofit.SupabaseService
import com.cleartab.cleartab.ui.criar_projeto.criar_projeto
import com.cleartab.cleartab.ui.projetos.projetosAdapter
import com.cleartab.cleartab.utils.SharedPreferencesUtil
import kotlinx.coroutines.launch

class home : AppCompatActivity() {
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

        precycler.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        precycler.layoutManager = layoutManager

        // specify an viewAdapter (see also next example)
        lifecycleScope.launch {
            val myDataset = db.fetchProjectsList(
                idUtilizador = SharedPreferencesUtil.getIDs(
                    this@home,
                    "ID_UTILIZADOR"
                )
            ) // replace with your data
            val adapter = projetosAdapter(myDataset!!)
            precycler.adapter = adapter
        }

        padicionar.setOnClickListener {
            val intent = Intent(this, criar_projeto::class.java)
            startActivity(intent)
        }
    }
}