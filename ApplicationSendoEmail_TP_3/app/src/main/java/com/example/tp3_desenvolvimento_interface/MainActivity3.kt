package com.example.tp3_desenvolvimento_interface

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        findViewById<Button>(R.id.btnEnviar).setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)

            val txtEmail = findViewById<EditText>(R.id.txtEmailtela3)
            intent.putExtra("email", txtEmail.text.toString())

            val txtRemetente = findViewById<EditText>(R.id.txtRemententetela3)
            intent.putExtra("remetente", txtRemetente.text.toString())

            val txtMensagem = findViewById<EditText>(R.id.txtMensagemtela3)
            intent.putExtra("mensagem", txtMensagem.text.toString())

            startActivity(intent)
        }
    }
}