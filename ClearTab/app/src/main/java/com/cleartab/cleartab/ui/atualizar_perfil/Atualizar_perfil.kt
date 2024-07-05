package com.cleartab.cleartab.ui.atualizar_perfil

import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.launch
import androidx.lifecycle.lifecycleScope
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import com.cleartab.cleartab.R
import com.cleartab.cleartab.retrofit.SupabaseService
import com.cleartab.cleartab.utils.SharedPreferencesUtil

class atualizar_perfil : AppCompatActivity(){
    private lateinit var anome: EditText
    private lateinit var acargo: Spinner
    private lateinit var aemail: EditText
    private lateinit var aconfirmar: Button
    private lateinit var aretroceder: Button
    private val db = SupabaseService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.atualizar_perfil)

        anome = findViewById(R.id.editTextText4)
        acargo = findViewById(R.id.spinner1)
        aemail = findViewById(R.id.editTextText6)
        aconfirmar = findViewById(R.id.button4)
        aretroceder = findViewById(R.id.button6)



        aconfirmar.setOnClickListener {
            val email = aemail.text.toString()
            val nome = anome.text.toString().trim()
            val cargo = acargo.selectedItemPosition

            if (email.isNotEmpty() && nome.isNotEmpty() && cargo >= 0) {
                lifecycleScope.launch {
                    val idUtilizador: Long = SharedPreferencesUtil.getIDs(this@atualizar_perfil, "ID_UTILIZADOR")
                    val idProjeto: Long = SharedPreferencesUtil.getIDs(this@atualizar_perfil, "ID_PROJETO")
                    val userProfile = db.fetchProfile(idUtilizador, idProjeto)
                }
            } else {
                Toast.makeText(this, "Todos os campos devem ser preenchidos", Toast.LENGTH_LONG).show()
            }
        }

        aretroceder.setOnClickListener{
            val email = aemail.text.toString()
            val nome = anome.text.toString().trim()
            val cargo = acargo.selectedItemPosition

            if(email.isNotEmpty() && nome.isNotEmpty() && cargo >= 0) {
                lifecycleScope.launch {
                    val idUtilizador: Long = SharedPreferencesUtil.getIDs(this@atualizar_perfil, "ID_UTILIZADOR")
                    val idProjeto: Long = SharedPreferencesUtil.getIDs(this@atualizar_perfil, "ID_PROJETO")
                    val userProfile = db.fetchProfile(idUtilizador, idProjeto)
                }
            } else {
                showError("Por favor preencha todos os campos")
                Log.e("Login","Campos de email ou senha vazios")
            }
        }
    }
    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        aemail.error = message
        anome.error = message
        Log.e("Login", message)
    }
}