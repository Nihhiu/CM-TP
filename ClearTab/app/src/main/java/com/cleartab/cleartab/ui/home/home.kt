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
import com.cleartab.cleartab.ui.eliminar_projeto.eliminar_projeto
import com.cleartab.cleartab.ui.projetos.projetosAdapter
import com.cleartab.cleartab.utils.SharedPreferencesUtil
import kotlinx.coroutines.launch

class home : AppCompatActivity() {
    private lateinit var heditar: Button
    private lateinit var hbaixar: Button
    private lateinit var hmembros: RecyclerView
    private val db = SupabaseService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.fragment_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.home)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        heditar = findViewById(R.id.button3)
        hbaixar = findViewById(R.id.button5)

        hmembros.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        hmembros.layoutManager = layoutManager

        // specify an viewAdapter (see also next example)
        lifecycleScope.launch {
            val myDataset = db.fetchProjectsList(
                idUtilizador = SharedPreferencesUtil.getIDs(
                    this@home,
                    "ID_UTILIZADOR"
                )
            ) // replace with your data
            val adapter = projetosAdapter(myDataset!!)
            hmembros.adapter = adapter
        }

        heditar.setOnClickListener {
            // TODO eliminar projeto
            val intent = Intent(this, eliminar_projeto::class.java)
            startActivity(intent)
        }
    }
}