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

class Page1 : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_page1, container, false)
        val bundle = Bundle()
        val navController = findNavController()

        avancar_button = view.findViewById<Button>(R.id.btnAvancarTela1)
        avancar_button.setOnClickListener {
            navController.navigate(R.id.action_page1_to_page2)
        }
        botoes_radios = arrayOf(
            view.findViewById<RadioButton>(R.id.radioButton),
            view.findViewById<RadioButton>(R.id.radioButton2),
            view.findViewById<RadioButton>(R.id.radioButton3),
            view.findViewById<RadioButton>(R.id.radioButton4)
        )
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

                Toast.makeText(view.context,
                    "Você clicou na alternativa:  ${botoes_radios[i].text}, Valor Total: $valorPage",
                    Toast.LENGTH_LONG).show()
            }
        }
        return view
    }

}