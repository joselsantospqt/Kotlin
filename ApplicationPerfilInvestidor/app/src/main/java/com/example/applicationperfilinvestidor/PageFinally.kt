package com.example.applicationperfilinvestidor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

private const val ARG_PARAM1 = "valor"
private const val ARG_PARAM2 = "nome"
private var param1: Int? = null
private var param2: String? = null

/**
 * A simple [Fragment] subclass.
 * Use the [PageFinally.newInstance] factory method to
 * create an instance of this fragment.
 */
class PageFinally : Fragment() {

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
        var view = inflater.inflate(R.layout.fragment_page_finally, container, false)
        val txtNome = view.findViewById<TextView>(R.id.txtNomeFinal)
        val txtpontuacao = view.findViewById<TextView>(R.id.txtPontuacao)

        if(param1.toString().toInt() <= 12){
            txtpontuacao.text = "Pefil : Conservador"
        }else if(param1.toString().toInt() >= 12 && param1.toString().toInt() <= 29){
            txtpontuacao.text = "Pefil : Moderado"
        }else if(param1.toString().toInt() >= 30){
            txtpontuacao.text = "Pefil : Arrojado"
        }


        txtNome.text = param2.toString()


        return view
    }

}