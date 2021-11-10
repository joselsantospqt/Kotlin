package com.example.app_navegacao

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnTela2 = findViewById<Button>(R.id.btnTela2)
        btnTela2.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            val txtnome = findViewById<EditText>(R.id.txtNome)
            intent.putExtra("nome", txtnome.text.toString())

            startActivity(intent)

        }
    }
}