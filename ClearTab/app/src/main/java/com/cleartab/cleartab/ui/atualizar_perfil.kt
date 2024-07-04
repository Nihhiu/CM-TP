package com.cleartab.cleartab.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.cleartab.cleartab.R


class atualizar_perfil : AppCompatActivity(){
    private lateinit var nome: EditText
    private lateinit var cargo: Spinner
    private lateinit var email: EditText
    private lateinit var confirmar: Button
    private lateinit var eliminar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.atualizar_perfil)

        nome = findViewById(R.id.editTextText4)
        cargo = findViewById(R.id.spinner1)
        email = findViewById(R.id.editTextText6)
        confirmar = findViewById(R.id.button4)
        eliminar = findViewById(R.id.button6)


        confirmar.setOnClickListener {
            val cemail = UserInfo.getInstance().email
            val cnome = nome.text.toString().trim()

            val selectedTypeIndex = cargo.selectedItemPosition

            if (cemail != null && userFirstName.isNotEmpty() && userLastName.isNotEmpty()) {
                updateUser(userEmail, userUsername, fullName, selectedTypeId.toString())
            } else {
                Toast.makeText(this, "Todos os campos devem ser preenchidos", Toast.LENGTH_LONG).show()
            }
        }
    }
}