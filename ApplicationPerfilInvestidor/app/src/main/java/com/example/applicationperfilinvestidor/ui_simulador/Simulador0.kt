package com.example.applicationperfilinvestidor.ui_simulador

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.applicationperfilinvestidor.R


private lateinit var avancar_button: Button
private lateinit var bundle: Bundle
private const val ARG_PARAM2 = "nome"
private var param2: String? = null


class Simulador0 : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param2 = it.getString(ARG_PARAM2)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_simulador0, container, false)
        val navController = findNavController()
        val txtNome = view.findViewById<EditText>(R.id.txtNome)
        bundle = Bundle()
        avancar_button = view.findViewById<Button>(R.id.btnIniciar)
        avancar_button.setOnClickListener {
            if (txtNome.text.toString().length <= 4)
                Toast.makeText(
                    view.context,
                    "Escreva um nome valido para continuar !!",
                    Toast.LENGTH_LONG
                ).show()
            else
            {
                newInstance(txtNome.text.toString())
                navController.navigate(R.id.action_simulador0_to_simulador1, bundle)
            }
        }
        return view
    }

    companion object {

        @JvmStatic
        fun newInstance(param2: String) =
            Simulador0().apply {
                arguments = bundle.apply {
                    putString(ARG_PARAM2, param2)
                }
            }
    }


}