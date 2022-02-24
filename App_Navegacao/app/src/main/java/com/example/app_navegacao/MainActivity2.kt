package com.example.app_navegacao

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val nome = intent.getStringExtra("idade")
        var lblIdadeInformado = findViewById<TextView>(R.id.lblIdadeInformado)
        lblIdadeInformado.setText(nome)

    }
}