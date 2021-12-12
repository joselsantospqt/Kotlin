package com.example.applicationperfilinvestidor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.applicationperfilinvestidor.ui_simulador.ActivitySimulador
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent(this, ActivitySimulador::class.java)
        btnLogin.setOnClickListener {
            startActivity(intent)
        }
    }
}