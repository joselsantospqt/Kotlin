package com.example.applicationquemvotei

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

private lateinit var txtInfo: TextView

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        txtInfo = findViewById<TextView>(R.id.txtInfo)
        txtInfo.setOnClickListener {
            Log.i("ADS", "Iniciando Intent Ligação")
            val callIntent : Intent = Uri.parse("tel:21999999999").let { number ->
                Intent(Intent.ACTION_CALL, number)
            }
            startActivity(callIntent)
        }

        recuperarDados()
    }

    private fun recuperarDados() {
        val nome = intent.getStringExtra(NOME_EXTRA)
        val cidade = intent.getStringExtra(CIDADE_EXTRA)
        val idade = intent.getStringExtra(IDADE_EXTRA)

        findViewById<TextView>(R.id.txtNomeFinal).text = nome
        findViewById<TextView>(R.id.txtLocalidade).text = cidade
        findViewById<TextView>(R.id.txtAnos).text = idade


        findViewById<TextView>(R.id.txtInfo).text = when(deveVotar(idade.toString().toInt())){
            FACULTATIVO -> "Seu voto será FACULTATIVO na próxima eleição"
            OBRIGATORIO -> "Seu voto será OBRIGATÓRIO na próxima eleição"
            else -> "Menor de 16? Como veio parar aqui?"
        }
    }
}