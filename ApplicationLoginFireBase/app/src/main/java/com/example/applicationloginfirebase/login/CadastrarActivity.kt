package com.example.applicationloginfirebase.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import com.example.applicationloginfirebase.R
import com.example.applicationloginfirebase.databinding.ActivityCadastrarBinding
import com.example.applicationloginfirebase.databinding.ActivityLoginBinding
import com.example.applicationloginfirebase.viewModel.MainViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import androidx.activity.viewModels


class CadastrarActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityCadastrarBinding
    var nome: String = ""
    var sobrenome: String = ""
    var email: String = ""
    var senha: String = ""
    var confirmaSenha: String = ""

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastrar)
        binding = ActivityCadastrarBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setup()

        auth = Firebase.auth

    }


    fun createUser() {
        auth = Firebase.auth

        auth.createUserWithEmailAndPassword(email, senha)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    finish()
                } else {
                    Log.i("login", "Falha ao criar", task.exception)
                    Toast.makeText(this, "Autenticação falhou", Toast.LENGTH_SHORT).show()
                }
            }
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {

        } else {

        }
    }

    fun verificacao(): Boolean {
        return (senha.length > 3 &&
                email.length > 5 &&
                nome.length > 2 &&
                sobrenome.length > 2 &&
                confirmaSenha != senha
                )
    }

    fun setup() {
        setuptextChange()
        setupButton()

    }

    fun setupButton() {
        binding.btnCadastrar.setOnClickListener {
            if (verificacao()) {
                createUser()

            } else {
                Toast.makeText(this, "Dados Incorretos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun setuptextChange() {
        binding.textInputNome.doAfterTextChanged { it ->
            if (!it.isNullOrEmpty() && !it.isBlank()) {
                Log.i("Nome", "${it}")
                nome = it.toString()
            } else {
                Log.i("Nome", "Vazia")
            }
        }

        binding.textInputSobrenome.doAfterTextChanged { it ->
            if (!it.isNullOrEmpty() && !it.isBlank()) {
                Log.i("Sobrenome", "${it}")
                sobrenome = it.toString()
            } else {
                Log.i("Sobrenome", "Vazia")
            }
        }

        binding.textInputEmail.doAfterTextChanged { it ->
            if (!it.isNullOrEmpty() && !it.isBlank()) {
                Log.i("Email", "${it}")
                email = it.toString()
            } else {
                Log.i("Email", "Vazia")
            }
        }


        binding.textInputSenha.doAfterTextChanged { it ->
            if (!it.isNullOrEmpty() && !it.isBlank()) {
                Log.i("Senha", "${it}")
                senha = it.toString()
            } else {
                Log.i("Senha", "Vazia")
            }
        }


        binding.textInputConfirmaSenha.doAfterTextChanged { it ->
            if (!it.isNullOrEmpty() && !it.isBlank()) {
                Log.i("Confirmar Senha", "${it}")
                confirmaSenha = it.toString()
            } else {
                Log.i("Confirmar Senha", "Vazia")
            }
        }
    }
}