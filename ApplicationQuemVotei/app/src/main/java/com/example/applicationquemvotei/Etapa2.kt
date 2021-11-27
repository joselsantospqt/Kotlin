package com.example.applicationquemvotei

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController

class Etapa2 : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_etapa2, container, false)
        val navController = findNavController()
        val txtIdade = view.findViewById<TextView>(R.id.txtIdade)
        val btn_cadastrar = view.findViewById<Button>(R.id.btnCadastrar)
        val idade = arguments?.get("idade").toString()
        txtIdade.text = "$idade anos"
        btn_cadastrar.setOnClickListener {
            val txtNome = view.findViewById<TextView>(R.id.txtNomeFinal)
            val txtCidade = view.findViewById<TextView>(R.id.txtCidade)
            val nome = txtNome.text.toString()
            val cidade = txtCidade.text.toString()
            val bundle = Bundle()
            bundle.putString("nome", nome)
            bundle.putString("cidade", cidade)
            bundle.putString("idade", idade)
            navController.navigate(R.id.action_etapa2_to_etapa3, bundle)
        }
        return view
    }



}