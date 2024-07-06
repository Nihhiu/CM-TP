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
import com.cleartab.cleartab.R.*
import com.cleartab.cleartab.retrofit.SupabaseService
import com.cleartab.cleartab.utils.SharedPreferencesUtil

class atualizar_perfil : AppCompatActivity(){
    private lateinit var anome: EditText
    private lateinit var acargo: Spinner
    private lateinit var aemail: EditText
    private lateinit var aconfirmar: Button
    private lateinit var aeliminar: Button
    private val db = SupabaseService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(layout.atualizar_perfil)

        anome = findViewById(id.ap_nome)
        acargo = findViewById(id.ap_cargo)
        aemail = findViewById(id.ap_email)
        aconfirmar = findViewById(id.ap_confirmar)
        aeliminar = findViewById(id.ap_eliminar)



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

        aeliminar.setOnClickListener{
            // TODO eliminar utilizador
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