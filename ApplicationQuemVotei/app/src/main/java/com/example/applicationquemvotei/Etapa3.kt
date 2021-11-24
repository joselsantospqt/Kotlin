package com.example.applicationquemvotei

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

private lateinit var txtInfo: TextView

class Etapa3 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_etapa3, container, false)

        txtInfo = view.findViewById<TextView>(R.id.txtInfo)
        txtInfo.setOnClickListener {
            Log.i("ADS", "Iniciando Intent Ligação")
            val callIntent : Intent = Uri.parse("tel:21999999999").let { number ->
                Intent(Intent.ACTION_CALL, number)
            }
            startActivity(callIntent)
        }

        val nome = "" //getActivity().getIntent().getExtras().getString(NOME_EXTRA)
        val cidade = "" //getActivity().getIntent().getExtras().getString(CIDADE_EXTRA)
        val idade = " " //getActivity().getIntent().getExtras().getString(IDADE_EXTRA)

        view.findViewById<TextView>(R.id.txtNomeFinal).text = nome
        view.findViewById<TextView>(R.id.txtLocalidade).text = cidade
        view.findViewById<TextView>(R.id.txtAnos).text = idade


        view.findViewById<TextView>(R.id.txtInfo).text = when(deveVotar(idade.toString().toInt())){
            FACULTATIVO -> "Seu voto será FACULTATIVO na próxima eleição"
            OBRIGATORIO -> "Seu voto será OBRIGATÓRIO na próxima eleição"
            else -> "Menor de 16? Como veio parar aqui?"
        }

        return view
    }
}