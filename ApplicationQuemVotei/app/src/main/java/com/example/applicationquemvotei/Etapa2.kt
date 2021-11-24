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
import java.util.zip.Inflater

class Etapa2 : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_etapa2, container, false)
       // val navController = findNavController()

        val txtIdade = view.findViewById<TextView>(R.id.txtIdade)
        val btn_cadastrar = view.findViewById<Button>(R.id.btnCadastrar)
        //val idade = getActivity().getIntent().getExtras().getString(IDADE_EXTRA);
        //txtIdade.text = idade
        btn_cadastrar.setOnClickListener {
            val txtNome = view.findViewById<TextView>(R.id.txtNomeFinal)
            val txtCidade = view.findViewById<TextView>(R.id.txtCidade)
            val nome = txtNome.text.toString()
            val cidade = txtCidade.text.toString()

            //val profileIntent = Intent(view.context, ProfileActivity::class.java)
            //profileIntent.putExtra(NOME_EXTRA, nome)
            //profileIntent.putExtra(CIDADE_EXTRA, cidade)
            //profileIntent.putExtra(IDADE_EXTRA, getActivity().getIntent().getExtras().getString(IDADE_EXTRA))
            //startActivity(profileIntent)
            //navController.navigate(R.id.action_etapa2_to_etapa3)
        }
        return view
    }



}