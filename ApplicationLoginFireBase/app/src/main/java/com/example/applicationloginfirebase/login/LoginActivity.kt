package com.example.applicationloginfirebase.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import com.example.applicationloginfirebase.R
import com.example.applicationloginfirebase.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityLoginBinding
    var login: String = ""
    var senha: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        auth = Firebase.auth
        setup()
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            Log.i("Login", "usuario logado")
        } else {
            Log.i("Login", "usuario não logado")

        }
    }

    fun setup() {
        setuptextChange()
        setupButton()
    }

    fun setupButton() {
        binding.btnCadastrar.setOnClickListener {
            var intent = Intent(this, CadastrarActivity::class.java)
            startActivity(intent)
        }
    }

    fun setuptextChange() {
        binding.textInputLogin.doAfterTextChanged { it ->
            if (!it.isNullOrEmpty() && it.isBlank()) {
                Log.i("Login", "login ${it}")
                login = it.toString()
            } else {
                Log.i("Login", "Login Vazia")
            }
        }

        binding.textInputSenha.doAfterTextChanged { it ->
            if (!it.isNullOrEmpty() && it.isBlank()) {
                Log.i("Senha", "Senha ${it}")
                senha = it.toString()
            } else {
                Log.i("Senha", "Senha Vazia")
            }
        }
    }
}