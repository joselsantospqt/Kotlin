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

class Etapa3 : Fragment() {

    private lateinit var txtInfo: TextView

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

        val nome = arguments?.get(NOME_EXTRA).toString()
        val cidade = arguments?.get(CIDADE_EXTRA).toString()
//        val idade = arguments?.get(IDADE_EXTRA).toString()
        val idade = "11"

        view.findViewById<TextView>(R.id.txtNomeFinal).text = nome
        view.findViewById<TextView>(R.id.txtLocalidade).text = cidade
        view.findViewById<TextView>(R.id.txtAnos).text = idade


        view.findViewById<TextView>(R.id.txtInfo).text = when(deveVotar(idade.toInt())){
            FACULTATIVO -> "Seu voto será FACULTATIVO na próxima eleição"
            OBRIGATORIO -> "Seu voto será OBRIGATÓRIO na próxima eleição"
            else -> "Menor de 16? Como veio parar aqui?"
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Lista de TextViews que exibirão o texto das perguntas
//        val perguntasTextViews = listOf(p1_textview,
//            p2_textview,
//            p3_textview,
//            p4_textview,
//            p5_textview)
//        // Lista de TextViews que exibirão o texto das respostas
//        val respostasTextViews = listOf(r1_textview,
//            r2_textview,
//            r3_textview,
//            r4_textview,
//            r5_textview)
//        // Configura cada texto iterativamente
//        for (i in duvidas.indices){
//            perguntasTextViews[i].text = duvidas[i].pergunta
//            respostasTextViews[i].text = duvidas[i].resposta
//        }
    }
}
