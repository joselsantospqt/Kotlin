package com.example.applicationperfilinvestidor.ui_emprestimo

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.applicationperfilinvestidor.R
import com.example.applicationperfilinvestidor.dados.Emprestimo
import com.example.applicationperfilinvestidor.dados.SolicitacoesDB
import com.example.applicationperfilinvestidor.listas.EmprestimoAdapter

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ListaEmprestimos : Fragment() {
    private lateinit var viewModel: ListaEmprestimosViewModel
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onResume() {
        var a = arguments?.getString(ARG_PARAM1)
        super.onResume()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.lista_emprestimos_fragment, container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = SolicitacoesDB.getInstance(application).emprestimoDao
        val factory = ListaEmprestimosViewModelFactory(dataSource, application)
        viewModel = ViewModelProvider(this, factory).get(ListaEmprestimosViewModel::class.java)

        val adapter = EmprestimoAdapter()
        val lista = root.findViewById<RecyclerView>(R.id.lstEmprestimos)
        lista.layoutManager = LinearLayoutManager(application)
        lista.adapter = adapter
        viewModel.emprestimos.observe(viewLifecycleOwner, Observer {
            it?.let{
                adapter.dados = it
            }
        })


        val edtNome = root.findViewById<EditText>(R.id.edtNome)
        val edtTelefone = root.findViewById<EditText>(R.id.edtTelefone)
        val edtEmail = root.findViewById<EditText>(R.id.edtEmail)
        val btnIncluir = root.findViewById<Button>(R.id.btnIncluir)

        btnIncluir.setOnClickListener {
            val contato = Emprestimo(0L, edtNome.toString(), edtTelefone.toString(), edtEmail.toString())
            viewModel.incluir(contato)
        }

        return root
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Emprestimo1.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ListaEmprestimos().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(ListaEmprestimosViewModel::class.java)
//        // TODO: Use the ViewModel
//    }

}