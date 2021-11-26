package com.example.applicationquemvotei

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController


class Etapa1 : Fragment() {

    val TAG = "QUEM VOTEI"

    private lateinit var idade_edittext: TextView
    private lateinit var avancar_button: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_etapa1, container, false)
        val bundle = Bundle()
        val navController = findNavController()

        idade_edittext = view.findViewById<TextView>(R.id.idade_edittext)
        avancar_button = view.findViewById<Button>(R.id.avancar_button)
        avancar_button.setOnClickListener {
            val idade = idade_edittext.text.toString()
            val status = deveVotar(idade.toInt())
            if (status == PROIBIDO){
                Toast.makeText(view.context,
                    "Você NÃO PODE usar este APP ainda!",
                    Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(view.context,
                    "Vamos prosseguir com o seu perfil!",
                    Toast.LENGTH_LONG).show()
                bundle.putString("teste", idade)
                navController.navigate(R.id.action_etapa1_to_etapa2)
            }
            // muda o texto escrito no botão
            avancar_button.text = "Apertou"
        }
        return view
    }
}