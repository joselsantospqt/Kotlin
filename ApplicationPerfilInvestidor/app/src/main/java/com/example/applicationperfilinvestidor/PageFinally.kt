package com.example.applicationperfilinvestidor

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_page_finally.*

private lateinit var avancar_emprestimo: Button
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
            txtpontuacao.text = "Perfil : Conservador"
        }else if(param1.toString().toInt() >= 13 && param1.toString().toInt() <= 29){
            txtpontuacao.text = "Perfil : Moderado"
        }else if(param1.toString().toInt() >= 30){
            txtpontuacao.text = "Perfil : Arrojado"
        }

        avancar_emprestimo = view.findViewById<Button>(R.id.btnEmprestimo)
        avancar_emprestimo.setOnClickListener {
            val intent = Intent(view.context, ActivityEmprestimo::class.java)
            intent.putExtra("nome", txtNome.text.toString())
            startActivity(intent)
        }


        txtNome.text = param2.toString()


        return view
    }

}