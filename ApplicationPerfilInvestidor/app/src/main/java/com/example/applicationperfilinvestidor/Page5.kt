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
class Page5 : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_page5, container, false)

        val bundle = Bundle()
        val navController = findNavController()

        botoes_radios = arrayOf(
            view.findViewById<RadioButton>(R.id.radioButton16),
            view.findViewById<RadioButton>(R.id.radioButton17),
            view.findViewById<RadioButton>(R.id.radioButton18)
        )
        avancar_button = view.findViewById<Button>(R.id.btnAvancarTela5)
        avancar_button.setOnClickListener {
            if(botoes_radios[0].isChecked ||
                botoes_radios[1].isChecked ||
                botoes_radios[2].isChecked)
                navController.navigate(R.id.action_page5_to_page6)
            else
                Toast.makeText(view.context,
                    "Você precisa selecionar uma opção !!",
                    Toast.LENGTH_LONG).show()

        }
        for(i in 0..2){
            botoes_radios[i].setOnClickListener{

                if(i == 0){
                    valorPage = 0
                }
                else if(i == 1){
                    valorPage = 0
                    valorPage = 2
                }
                else{
                    valorPage = 0
                    valorPage = 4
                }

                Toast.makeText(view.context,
                    "Você clicou na alternativa:  ${botoes_radios[i].text}, Valor Total: $valorPage",
                    Toast.LENGTH_LONG).show()
            }
        }
        return view
    }

}