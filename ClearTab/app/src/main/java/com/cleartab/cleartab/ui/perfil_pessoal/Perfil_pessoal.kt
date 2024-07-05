package com.cleartab.cleartab.ui.perfil_pessoal

import android.content.Intent
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.launch
import androidx.lifecycle.lifecycleScope
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cleartab.cleartab.R
import com.cleartab.cleartab.retrofit.SupabaseService
import com.cleartab.cleartab.utils.*
import com.cleartab.cleartab.utils.SharedPreferencesUtil
class perfil_pessoal: AppCompatActivity() {
    private lateinit var pnome: EditText
    private lateinit var pcargo: EditText
    private lateinit var pemail: EditText
    private lateinit var peditar: Button
    private lateinit var psair: Button
    private val db = SupabaseService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.perfil_pessoal)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.perfil_pessoal)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        pnome = findViewById(R.id.editTextText4)
        pcargo = findViewById(R.id.editTextText5)
        pemail = findViewById(R.id.editTextText6)
        peditar = findViewById(R.id.button3)
        psair = findViewById(R.id.button5)

        //Esta informação tem de substituir as outras caixas de texto
        peditar.setOnClickListener {
            val nome = pnome.text.toString()
            val cargo = pcargo.text.toString()
            val email = pemail.text.toString()

            if(nome.isNotEmpty() && cargo.isNotEmpty() && email.isNotEmpty()) {
                lifecycleScope.launch {
                    val idUtilizador = SharedPreferencesUtil.getIDs(this@perfil_pessoal, "ID_UTILIZADOR")
                    val idProjeto = SharedPreferencesUtil.getIDs(this@perfil_pessoal, "ID_PROJETO")
                    val response = db.fetchProfile(idUtilizador, idProjeto)
                }
            }
        }

        psair.setOnClickListener {
            val intent = Intent(this, home::class.java)
            startActivity(intent)
        }
    }
}