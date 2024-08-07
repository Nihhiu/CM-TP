package com.cleartab.cleartab.ui.criar_projeto

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.cleartab.cleartab.MainActivity
import com.cleartab.cleartab.R.*
import com.cleartab.cleartab.retrofit.SupabaseService
import com.cleartab.cleartab.retrofit.tables.Projeto
import com.cleartab.cleartab.utils.SharedPreferencesUtil
import kotlinx.coroutines.launch

class criar_projeto : AppCompatActivity() {
    private lateinit var cnome : EditText
    private lateinit var cdescricao : EditText
    private lateinit var cmembros : EditText
    private lateinit var cconfirmar : Button
    private val db = SupabaseService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(layout.criar_projeto)

        cnome = findViewById(id.cp_nome)
        cdescricao = findViewById(id.cp_descricao)
        cmembros = findViewById(id.cp_membros)
        cconfirmar = findViewById(id.cp_confirmar)

        cconfirmar.setOnClickListener {
            val cnome = cnome.text.toString()
            val cdescricao = cdescricao.text.toString()
            val cmembros = cmembros.text.toString()
            val projeto = Projeto(nome = cnome, descricao = cdescricao)
            val idUtilizador = SharedPreferencesUtil.getIDs(this@criar_projeto, "ID_UTILIZADOR")
            if (cnome.isNotEmpty() && cdescricao.isNotEmpty() && cmembros.isNotEmpty()) {
                lifecycleScope.launch {
                    val idProjeto = db.createProject(projeto, idUtilizador)
                    val linha = cmembros.split("\n")
                    for (line in linha) {
                        val done = db.addUtilizadorToProject(line, idProjeto!!)
                    }
                }
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                showError("Por favor preencha todos os campos")
                Log.e("Confirmar", "Campos de nome, descrição ou membros vazios")
            }
        }
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        cnome.error = message
        cdescricao.error = message
        Log.e("Login", message)
    }
}