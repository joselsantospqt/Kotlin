package com.example.applicationperfilinvestidor.ui_simulador

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.applicationperfilinvestidor.R


private var valorPage = 0
private lateinit var avancar_button: Button
private lateinit var voltar_button: Button
private lateinit var botoes_radios: Array<RadioButton>
private lateinit var bundle: Bundle
private const val ARG_PARAM1 = "valor"
private const val ARG_PARAM2 = "nome"
private var param1: Int? = null
private var param2: String? = null



class Simulador7 : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_simulador7, container, false)
        val navController = findNavController()
        bundle = Bundle()

        avancar_button = view.findViewById<Button>(R.id.btnAvancarTela7)
        avancar_button.setOnClickListener {
            navController.navigate(R.id.action_simulador7_to_simulador8, bundle)
        }
        botoes_radios = arrayOf(
            view.findViewById<RadioButton>(R.id.radioButton23),
            view.findViewById<RadioButton>(R.id.radioButton24),
            view.findViewById<RadioButton>(R.id.radioButton25),
            view.findViewById<RadioButton>(R.id.radioButton26)
        )
        voltar_button = view.findViewById<Button>(R.id.btnVoltarTela7)
        voltar_button.setOnClickListener {
            navController.navigate(R.id.action_simulador7_to_simulador6)
        }
        for(i in 0..3){
            botoes_radios[i].setOnClickListener{

                if(i == 0){
                    valorPage = 0
                }
                else if(i == 1){
                    valorPage = 0
                    valorPage = 2
                }
                else if(i == 2){
                    valorPage = 0
                    valorPage = 3
                }
                else{
                    valorPage = 0
                    valorPage = 4
                }

                var valorRecuperado = param1.toString().toInt() + valorPage
                newInstance(valorRecuperado.toString(), param2.toString())
                Toast.makeText(view.context,
                    "Voc?? clicou na alternativa:  ${botoes_radios[i].text}",
                    Toast.LENGTH_LONG).show()
            }
        }
        return view
    }
    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Simulador7().apply {
                arguments = bundle.apply {
                    putInt(ARG_PARAM1, param1.toInt())
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}