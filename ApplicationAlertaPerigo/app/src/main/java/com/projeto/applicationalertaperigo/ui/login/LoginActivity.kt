package com.projeto.applicationalertaperigo.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import com.projeto.applicationalertaperigo.R
import com.projeto.applicationalertaperigo.databinding.ActivityLoginBinding
import com.projeto.applicationalertaperigo.ui.home.HomeActivity
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
            var intent = Intent(applicationContext, HomeActivity::class.java)
            startActivity(intent)
        } else {
            Log.i("Login", "usuario não logado")

        }
    }

    private fun setup() {
        setuptextChange()
        setupButton()
    }

    private fun setupButton() {
        binding.btnCadastrar.setOnClickListener {
            var intent = Intent(this, LoginCadastrarActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {
            if (!binding.textInputLogin.text.isNullOrEmpty() && !binding.textInputSenha.text.isNullOrEmpty())
                logarFirebase(
                    binding.textInputLogin.text.toString(),
                    binding.textInputSenha.text.toString()
                )

            //IMPLEMENTAR O ANUNCIOS CASO ELE ERRE A SENHA 3 VEZES
        }
    }

    private fun setuptextChange() {
        binding.textInputLogin.doAfterTextChanged { it ->
            if (!it.isNullOrEmpty() && it.isBlank()) {
                login = it.toString()
            } else {
                Log.i("Login", "Login Vazia")
            }
        }
        binding.textInputSenha.doAfterTextChanged { it ->
            if (!it.isNullOrEmpty() && it.isBlank()) {
                senha = it.toString()
            } else {
                Log.i("Senha", "Senha Vazia")
            }
        }
    }

    private fun logarFirebase(email: String, senha: String) {
        auth.signInWithEmailAndPassword(email, senha)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.i("Login", "signInWithEmail:success")
                    //val user = auth.currentUser
                    chamarHome()
                    Toast.makeText(
                        this, "Login realizado com sucesso !!.",
                        Toast.LENGTH_SHORT
                    ).show()

                } else {
                    // If sign in fails, display a message to the user.
                    Log.i("Login", "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        this, "Autenticação Falhou.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

    }

    private fun chamarHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}