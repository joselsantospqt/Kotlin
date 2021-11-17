package com.example.applicationquemvotei

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        recuperarDados()
    }

    private fun recuperarDados() {
        val nome = intent.getStringExtra(NOME_EXTRA)
        val cidade = intent.getStringExtra(CIDADE_EXTRA)
        val idade = intent.getStringExtra(IDADE_EXTRA)

        var nome_textview = findViewById<TextView>(R.id.txtNomeFinal)
        nome_textview.text = nome
        var cidade_textview = findViewById<TextView>(R.id.txtLocalidade)
        cidade_textview.text = cidade
        var idade_textview = findViewById<TextView>(R.id.txtAnos)
        idade_textview.text = idade

        var status_textview = findViewById<TextView>(R.id.txtInfo)

        status_textview.text = when(deveVotar(idade.toString().toInt())){
            FACULTATIVO -> "Seu voto será FACULTATIVO na próxima eleição"
            OBRIGATORIO -> "Seu voto será OBRIGATÓRIO na próxima eleição"
            else -> "Menor de 16? Como veio parar aqui?"
        }
    }

    private fun deveVotar(idade: Int): Int {
        when(idade) {
            in 18..69 -> return OBRIGATORIO
            in 0..15 -> return PROIBIDO
            else -> return FACULTATIVO
        }
    }
}