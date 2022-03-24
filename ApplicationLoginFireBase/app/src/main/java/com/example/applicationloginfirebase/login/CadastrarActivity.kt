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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastrar)
        binding = ActivityCadastrarBinding.inflate(layoutInflater)
        val view = binding.root
    }
}