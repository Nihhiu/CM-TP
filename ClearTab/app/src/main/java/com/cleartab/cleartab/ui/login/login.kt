package com.cleartab.cleartab.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.cleartab.cleartab.MainActivity
import com.cleartab.cleartab.R
import com.cleartab.cleartab.retrofit.SupabaseService
import com.cleartab.cleartab.ui.criar_conta.criar_conta
import com.cleartab.cleartab.ui.projetos.projetos
import com.cleartab.cleartab.utils.SharedPreferencesUtil
import kotlinx.coroutines.launch

class login : AppCompatActivity(){
    private lateinit var lemail : EditText
    private lateinit var lpassword : EditText
    private lateinit var llogin : Button
    private lateinit var lcriar_conta : Button
    private val db = SupabaseService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        lemail = findViewById(R.id.editTextText)
        lpassword = findViewById(R.id.editTextTextPassword)
        llogin = findViewById(R.id.button)
        lcriar_conta = findViewById(R.id.button6)

        // Criar conta
        lcriar_conta.setOnClickListener {
            val intent = Intent(this, criar_conta::class.java)
            startActivity(intent)
            finish()
        }

        // Botao Login
        llogin.setOnClickListener {
            val email = lemail.text.toString()
            val password = lpassword.text.toString()
            intent = Intent(this, MainActivity::class.java)

            if (email.isNotEmpty() && password.isNotEmpty()) {
                    lifecycleScope.launch {
                        val idUtilizador = db.logIn(email, password)
                        if (idUtilizador != null){
                            SharedPreferencesUtil.saveIds(this@login, "ID_UTILIZADOR", idUtilizador)
                            val projeto = db.fetchProject(idProjeto = 1)
                            SharedPreferencesUtil.saveIds(this@login, "ID_PROJETO", projeto?.idProjeto!!)

                            startActivity(intent)
                            finish()
                        } else {
                            showError("Email ou senha incorretos")
                        }
                    }
            } else {
                showError("Por favor, preencha todos os campos")
                Log.e("Login", "Campos de email ou senha vazios")
            }
        }
    }


    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        lemail.error = message
        lpassword.error = message
        Log.e("Login", message)
    }
}