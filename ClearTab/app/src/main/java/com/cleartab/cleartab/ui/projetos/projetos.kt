package com.cleartab.cleartab.ui.projetos

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cleartab.cleartab.R
import com.cleartab.cleartab.retrofit.SupabaseService
import com.cleartab.cleartab.retrofit.tables.Projeto
import com.cleartab.cleartab.ui.criar_projeto.criar_projeto
import com.cleartab.cleartab.utils.SharedPreferencesUtil
import kotlinx.coroutines.launch

class projetos: AppCompatActivity() {
    private lateinit var precycler: RecyclerView
    private lateinit var padicionar: Button
    private val db = SupabaseService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.projetos)

        precycler = findViewById(R.id.recycler)
        padicionar = findViewById(R.id.button2)

        precycler.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        precycler.layoutManager = layoutManager

        // specify an viewAdapter (see also next example)
        fetchProjects { myDataset ->
            if(myDataset?.isEmpty() == true){
                val intent = Intent(this, criar_projeto::class.java)
                startActivity(intent)
                finish()
            } else {
                val adapter = projetosAdapter(myDataset!!) {projeto ->
                    Toast.makeText(this, "Clicked: ${projeto.idProjeto}", Toast.LENGTH_SHORT).show()
                }
                precycler.adapter = adapter
            }
        }

        padicionar.setOnClickListener {
            val intent = Intent(this, criar_projeto::class.java)
            startActivity(intent)
        }
    }

    fun fetchProjects(callback: (List<Projeto>?) -> Unit) {
        lifecycleScope.launch {
            val myDataset = db.fetchProjectsList(
                idUtilizador = SharedPreferencesUtil.getIDs(
                    this@projetos,
                    "ID_UTILIZADOR"
                )
            )
            callback(myDataset)
        }
    }
}