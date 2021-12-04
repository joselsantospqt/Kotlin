package com.example.applicationperfilinvestidor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_emprestimo.*

class ActivityEmprestimo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emprestimo)

        val nome = intent.getStringExtra("nome")
        txtIntentNome.text = nome
    }
}