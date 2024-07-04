package com.cleartab.cleartab.ui.login

import android.content.Intent
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cleartab.cleartab.R
import com.cleartab.cleartab.retrofit.SupabaseAuthService.*
import com.cleartab.cleartab.retrofit.supabase
import io.github.jan.supabase.gotrue.auth
import io.ktor.util.reflect.instanceOf

class login : AppCompatActivity(){
    private lateinit var lemail : EditText
    private lateinit var lpassword : EditText
    private lateinit var llogin : Button
    private lateinit var lcriar_conta : TextView

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
        lcriar_conta = findViewById(R.id.textView5)

        // Criar conta
        lcriar_conta.setOnClickListener {
            val intent = Intent(this, criar_conta::class.java)
            startActivity(intent)
        }

        // Botao Login
        llogin.setOnClickListener {
            val email = lemail.text.toString()
            val password = lpassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                logIn(email, password)
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