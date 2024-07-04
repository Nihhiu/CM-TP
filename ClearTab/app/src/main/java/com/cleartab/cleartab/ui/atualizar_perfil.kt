package com.cleartab.cleartab.ui

import android.content.Intent
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import com.cleartab.cleartab.R
import com.cleartab.cleartab.retrofit.SupabaseAuthService.*
import com.cleartab.cleartab.retrofit.supabase
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.user.UserInfo

class atualizar_perfil : AppCompatActivity(){
    private lateinit var anome: EditText
    private lateinit var acargo: Spinner
    private lateinit var aemail: EditText
    private lateinit var aconfirmar: Button
    private lateinit var aeliminar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.atualizar_perfil)

        anome = findViewById(R.id.editTextText4)
        acargo = findViewById(R.id.spinner1)
        aemail = findViewById(R.id.editTextText6)
        aconfirmar = findViewById(R.id.button4)
        aeliminar = findViewById(R.id.button6)


        aconfirmar.setOnClickListener {
            val email = UserInfo.getInstance().email
            val nome = anome.text.toString().trim()
            val cargo = acargo.selectedItemPosition

            if (email.isNotEmpty() && nome.isNotEmpty() && cargo >= 0) {
                editProfile(email, nome, cargo)
            } else {
                Toast.makeText(this, "Todos os campos devem ser preenchidos", Toast.LENGTH_LONG).show()
            }
        }

        aeliminar.setOnClickListener{
            val email = UserInfo.getInstance().email
            val nome = anome.text.toString().trim()
            val cargo = acargo.selectedItemPosition

            if(email.isNotEmpty() && nome.isNotEmpty() && cargo == 0){
                removeProfile(email, nome, cargo)
            } else{
                Toast.makeText(this, "Todos os campos devem ser preenchidos", Toast.LENGTH_LONG).show()
            }
        }
    }
}