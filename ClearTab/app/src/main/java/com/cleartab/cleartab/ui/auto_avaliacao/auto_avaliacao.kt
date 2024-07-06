package com.cleartab.cleartab.ui.auto_avaliacao

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.cleartab.cleartab.R
import com.cleartab.cleartab.retrofit.SupabaseService

class auto_avaliacao : AppCompatActivity() {
    //TODO auto_avaliacao
    private lateinit var ahoras: EditText
    private lateinit var adificuldades: Spinner
    private lateinit var amembros: Spinner
    private lateinit var adescricao: Button
    private lateinit var aconfirmar: Button
    private val db = SupabaseService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.atualizar_perfil)

        ahoras = findViewById(R.id.aa_horas)
        adificuldades = findViewById(R.id.aa_dificuldade)
        amembros = findViewById(R.id.aa_membros)
        adescricao = findViewById(R.id.aa_descricao)
        aconfirmar = findViewById(R.id.aa_confirmar)
    }
}