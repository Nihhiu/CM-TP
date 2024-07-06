package com.cleartab.cleartab.ui.criar_tarefas

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cleartab.cleartab.R
import com.cleartab.cleartab.retrofit.SupabaseService

class criar_tarefas: AppCompatActivity() {
    //TODO
    private lateinit var ctitulo : EditText
    private lateinit var cdata : EditText
    private lateinit var cdescricao : EditText
    private lateinit var cmembros : Spinner
    private lateinit var cconfirmar : Spinner
    private val db = SupabaseService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.criar_projeto)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.criar_projeto)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        ctitulo = findViewById(R.id.ct_titulo)
        cdata = findViewById(R.id.ct_data)
        cdescricao = findViewById(R.id.ct_descricao)
        cmembros = findViewById(R.id.ct_membros)
        cconfirmar = findViewById(R.id.ct_confirmar)
    }
}