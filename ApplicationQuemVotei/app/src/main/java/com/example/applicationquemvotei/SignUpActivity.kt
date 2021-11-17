package com.example.applicationquemvotei

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        val txtIdade = findViewById<TextView>(R.id.txtIdade)
        val btn_cadastrar = findViewById<Button>(R.id.btnCadastrar)
        var idade = intent.getStringExtra(IDADE_EXTRA)
        txtIdade.text = idade
        btn_cadastrar.setOnClickListener {
            criarSessao()
        }

    }

    private fun criarSessao() {
        val txtNome = findViewById<TextView>(R.id.txtNomeFinal)
        val txtCidade = findViewById<TextView>(R.id.txtCidade)
        val nome = txtNome.text.toString()
        val cidade = txtCidade.text.toString()

        val profileIntent = Intent(this, ProfileActivity::class.java)
        profileIntent.putExtra(NOME_EXTRA, nome)
        profileIntent.putExtra(CIDADE_EXTRA, cidade)
        profileIntent.putExtra(IDADE_EXTRA, intent.getStringExtra(IDADE_EXTRA))

        startActivity(profileIntent)
    }

}