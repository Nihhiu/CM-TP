package com.cleartab.cleartab.ui.criar_tarefas

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.cleartab.cleartab.R.*
import com.cleartab.cleartab.retrofit.SupabaseService

class criar_tarefas: AppCompatActivity() {
    //TODO
    private lateinit var ctitulo : EditText
    private lateinit var cdata : EditText
    private lateinit var cdescricao : EditText
    private lateinit var cmembros : Spinner
    private lateinit var cconfirmar : Button
    private val db = SupabaseService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(layout.criar_tarefas)

        ctitulo = findViewById(id.ct_titulo)
        cdata = findViewById(id.ct_data)
        cdescricao = findViewById(id.ct_descricao)
        cmembros = findViewById(id.ct_membros)
        cconfirmar = findViewById(id.ct_confirmar)
    }
}