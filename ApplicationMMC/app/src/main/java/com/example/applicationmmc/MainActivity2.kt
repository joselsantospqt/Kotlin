package com.example.applicationmmc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity2 : AppCompatActivity() {

    private lateinit var btn_Voltar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val IMC = intent.getStringExtra("IMC")
        var lblIMCInformado = findViewById<TextView>(R.id.textResultadoIMC)
        lblIMCInformado.setText(IMC)

        btn_Voltar = findViewById<Button>(R.id.buttonVoltar)
        btn_Voltar.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }
}