package com.cleartab.cleartab.ui.criar_conta

import android.content.Intent
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.launch
import androidx.lifecycle.lifecycleScope
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cleartab.cleartab.R
import com.cleartab.cleartab.retrofit.SupabaseService
import com.cleartab.cleartab.ui.login.login
import com.cleartab.cleartab.retrofit.tables.*

class criar_conta : AppCompatActivity() {
    private lateinit var cemail : EditText
    private lateinit var cpassword : EditText
    private lateinit var ccriar_conta : Button
    private lateinit var cnome : EditText
    private lateinit var centre: TextView
    private val db = SupabaseService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.criar_conta)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.criar_conta)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        cemail = findViewById(R.id.cc_email)
        cpassword = findViewById(R.id.cc_password)
        cnome = findViewById(R.id.cc_nome)
        ccriar_conta = findViewById(R.id.cc_criar_conta)
        centre = findViewById(R.id.cc_entre)

        centre.setOnClickListener {
            val intent = Intent(this, login::class.java)
            startActivity(intent)
        }

        ccriar_conta.setOnClickListener {
            val cemail = cemail.text.toString()
            val cpassword = cpassword.text.toString()
            val cnome = cnome.text.toString()
            val utilizador = Utilizador(email = cemail, password = cpassword, nome = cnome, fotografia = null)

            if(cemail.isNotEmpty() && cpassword.isNotEmpty() && cnome.isNotEmpty()){
                lifecycleScope.launch {
                    val response = db.signUp(utilizador)
                }
            } else{
                showError("Por favor preencha todos os campos")
                Log.e("Login","Campos de email ou senha vazios")
            }
        }
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        cemail.error = message
        cpassword.error = message
        cnome.error = message
        Log.e("Login", message)
    }
}