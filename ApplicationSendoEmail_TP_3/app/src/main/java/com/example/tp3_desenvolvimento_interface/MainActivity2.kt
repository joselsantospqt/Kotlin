package com.example.tp3_desenvolvimento_interface

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val Remetente = intent.getStringExtra("remetente")
        var txtRementente = findViewById<TextView>(R.id.txtRemententetela2)
        txtRementente.setText(Remetente)

        val Email = intent.getStringExtra("email")
        var txtEmail = findViewById<TextView>(R.id.txtParatela2)
        txtEmail.setText(Email)

        val Mensagem = intent.getStringExtra("mensagem")
        var txtMensagem = findViewById<TextView>(R.id.txtMensagemtela2)
        txtMensagem.setText(Mensagem)
    }
}