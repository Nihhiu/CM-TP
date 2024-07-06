package com.cleartab.cleartab.ui.perfil_geral

import android.content.Intent
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cleartab.cleartab.R
import com.cleartab.cleartab.retrofit.SupabaseService
import com.cleartab.cleartab.ui.atualizar_perfil.atualizar_perfil
import com.cleartab.cleartab.utils.*

class perfil_geral : AppCompatActivity(){
    private lateinit var gnome: EditText
    private lateinit var gcargo: EditText
    private lateinit var gemail: EditText
    private lateinit var gbaixar: Button
    private lateinit var geditar: Button
    private val db = SupabaseService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.perfil_geral)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.perfil_geral)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        gnome = findViewById(R.id.pg_nome)
        gcargo = findViewById(R.id.pg_cargo)
        gemail = findViewById(R.id.pg_email)
        gbaixar = findViewById(R.id.pg_baixaar)
        geditar = findViewById(R.id.pg_editar)

        // Botão Baixar
        gbaixar.setOnClickListener {
            val intent = Intent(this, perfil_geral::class.java)
            startActivity(intent)
        }

        geditar.setOnClickListener {
            val nome = gnome.text.toString()
            val cargo = gcargo.text.toString()
            val email = gemail.text.toString()
            val idUtilizador = SharedPreferencesUtil.getIDs(this@perfil_geral, "ID_UTILIZADOR")
            val intent = Intent(this, atualizar_perfil::class.java)
            intent.putExtra("idUtilizador", idUtilizador)
            intent.putExtra("nome", nome)
            intent.putExtra("cargo", cargo)
            intent.putExtra("email", email)
            startActivity(intent)
        }
    }
}