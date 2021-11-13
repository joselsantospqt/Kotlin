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
const val IDADE_EXTRA = "idade"

class MainActivity : AppCompatActivity() {
    val TAG = "QUEM VOTEI"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val idade_edittext = findViewById<TextView>(R.id.idade_edittext)
        val avancar_button = findViewById<Button>(R.id.avancar_button)
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

                signupIntent.putExtra("idade", idade)

                startActivity(signupIntent)
            }
            // muda o texto escrito no botão
            avancar_button.text = "Apertou"
        }
    }

    fun deveVotar(idade: Int): Int {
        when (idade) {
            in 18..69 -> return OBRIGATORIO
            in 0..15 -> return PROIBIDO
            else -> return FACULTATIVO
        }
    }
}