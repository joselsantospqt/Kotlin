package com.example.applicationperfilinvestidor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.Toast
import androidx.navigation.fragment.findNavController

private var valorPage = 0
private lateinit var avancar_button: Button
private lateinit var botoes_radios: Array<RadioButton>
private lateinit var bundle: Bundle
private const val ARG_PARAM1 = "valor"
private const val ARG_PARAM2 = "nome"
private var param1: Int? = null
private var param2: String? = null


class Page3 : Fragment() {


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
        val view = inflater.inflate(R.layout.fragment_page3, container, false)
        bundle = Bundle()
        val navController = findNavController()

        botoes_radios = arrayOf(
            view.findViewById<RadioButton>(R.id.radioButton9),
            view.findViewById<RadioButton>(R.id.radioButton10),
            view.findViewById<RadioButton>(R.id.radioButton11),
            view.findViewById<RadioButton>(R.id.radioButton12)
        )
        avancar_button = view.findViewById<Button>(R.id.btnAvancarTela3)
        avancar_button.setOnClickListener {
            if(botoes_radios[0].isChecked ||
                botoes_radios[1].isChecked ||
                botoes_radios[2].isChecked ||
                botoes_radios[3].isChecked)
                navController.navigate(R.id.action_page3_to_page4)
            else
                Toast.makeText(view.context,
                    "Você precisa selecionar uma opção !!",
                    Toast.LENGTH_LONG).show()
        }
        for(i in 0..3){
            botoes_radios[i].setOnClickListener{

                if(i == 0){
                    valorPage = 0
                }
                else if(i == 1){
                    valorPage = 0
                    valorPage = 1
                }
                else if(i == 2){
                    valorPage = 0
                    valorPage = 2
                }
                else{
                    valorPage = 0
                    valorPage = 4
                }

                var valorRecuperado = param1.toString().toInt() + valorPage
                Page2.newInstance(valorRecuperado.toString(), param2.toString())
                Toast.makeText(view.context,
                    "Você clicou na alternativa:  ${botoes_radios[i].text}, Valor Total: $valorPage",
                    Toast.LENGTH_LONG).show()
            }
        }
        return view
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Page2().apply {
                arguments = bundle.apply {
                    putInt(ARG_PARAM1, param1.toInt())
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}