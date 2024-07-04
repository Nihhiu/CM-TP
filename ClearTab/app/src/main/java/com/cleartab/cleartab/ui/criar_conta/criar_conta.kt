package com.cleartab.cleartab.ui.criar_conta

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

class criar_conta : AppCompatActivity() {
    private lateinit var cemail : EditText
    private lateinit var cpassword : EditText
    private lateinit var ccriar_conta : Button
    private lateinit var cnome : EditText
    private lateinit var centre: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.criar_conta)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.criar_conta)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        cemail = findViewById(R.id.editTextTextEmailAddress)
        cpassword = findViewById(R.id.editTextTextPassword2)
        cnome = findViewById(R.id.editTextText)
        ccriar_conta = findViewById(R.id.button)
        centre = findViewById(R.id.textView30)

        centre.setOnClickListener {
            val intent: Intent(this, login::class.java)
            startActivity(intent)
        }

        ccriar_conta.setOnClickListener {
            val cemail = cemail.text.toString()
            val cpassword = cpassword.text.toString()
            val cnome = cnome.text.toString()

            if(cemail.isNotEmpty() && cpassword.isNotEmpty() && cnome.isNotEmpty()){
                signUp(cemail, cpassword, cnome)
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