package com.example.applicationquemvotei

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

const val PROIBIDO = -1
const val FACULTATIVO = 0
const val OBRIGATORIO = 1
const val NOME_EXTRA = "nome"
const val CIDADE_EXTRA = "cidade"
const val IDADE_EXTRA = "idade"

class MainActivity : AppCompatActivity() {
    val TAG = "QUEM VOTEI"

    private lateinit var idade_edittext: TextView
    private lateinit var avancar_button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        idade_edittext = findViewById<TextView>(R.id.idade_edittext)
        avancar_button = findViewById<Button>(R.id.avancar_button)
        avancar_button.setOnClickListener {
            val idade = idade_edittext.text.toString()
            val status = deveVotar(idade.toInt())
            if (status == PROIBIDO){
                Toast.makeText(this,
                    "Você NÃO PODE usar este APP ainda!",
                    Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this,
                    "Vamos prosseguir com o seu perfil!",
                    Toast.LENGTH_LONG).show()
                val signupIntent = Intent(this,
                    SignUpActivity::class.java)

                signupIntent.putExtra(IDADE_EXTRA, idade)

                startActivity(signupIntent)
            }
            // muda o texto escrito no botão
            avancar_button.text = "Apertou"
        }
    }
}