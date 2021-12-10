package com.example.applicationperfilinvestidor

import android.os.Bundle
import android.content.Intent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_emprestimo0.*
import kotlinx.android.synthetic.main.fragment_emprestimo0.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "nome"
private const val ARG_PARAM2 = "param2"
private var param1: String? = null
private var param2: String? = null
private lateinit var concluir_emprestimo: Button

/**
 * A simple [Fragment] subclass.
 * Use the [Emprestimo0.newInstance] factory method to
 * create an instance of this fragment.
 */
class Emprestimo0 : Fragment() {
    // TODO: Rename and change types of parameters

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_emprestimo0, container, false)
        val navController = findNavController()
        concluir_emprestimo = view.findViewById<Button>(R.id.btnSolicitaEmprestimo)
        concluir_emprestimo.setOnClickListener {
            navController.navigate(R.id.action_emprestimo0_to_emprestimo1)
        }

        return view
    }

    override fun onResume() {
        var nome = arguments?.getString("nome")
        var a = param1.toString()
        var b = param2.toString()
        textView12.text = nome
        super.onResume()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Emprestimo0.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Emprestimo0().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}