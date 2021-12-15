package com.example.applicationperfilinvestidor.ui_emprestimo

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.applicationperfilinvestidor.R
import com.example.applicationperfilinvestidor.dados.Emprestimo
import com.example.applicationperfilinvestidor.dados.SolicitacoesDB
import com.example.applicationperfilinvestidor.listas.EmprestimoAdapter


class ListaEmprestimos : Fragment() {
    private lateinit var viewModel: ListaEmprestimosViewModel
    private val viewModelFragment: FragmentViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.lista_emprestimos_fragment, container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = SolicitacoesDB.getInstance(application).emprestimoDao
        val factory = ListaEmprestimosViewModelFactory(dataSource, application)
        viewModel = ViewModelProvider(this, factory).get(ListaEmprestimosViewModel::class.java)

        //CARREGA A VIEW
        val adapter = EmprestimoAdapter()
        val lista = root.findViewById<RecyclerView>(R.id.lstEmprestimos)
        lista.layoutManager = LinearLayoutManager(application)
        lista.adapter = adapter
        viewModel.emprestimos.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.dados = it
            }
        })

        val edtNome = root.findViewById<EditText>(R.id.edtNome)
        val edtValor = root.findViewById<EditText>(R.id.edtValor)
        val edtStatus = root.findViewById<EditText>(R.id.edtStatus)
        val btnIncluir = root.findViewById<Button>(R.id.btnIncluir)

        //        CARREGA O NOME NO COMPONENTE

        viewModelFragment.data.observe(viewLifecycleOwner, {
            if (it.count() == 2) {
                edtNome.setText(it[0])
                edtStatus.setText(it[1])
            }
        })


        btnIncluir.setOnClickListener {
            val contato = Emprestimo(
                0L,
                edtNome.text.toString(),
                "R$ ${edtValor.text.toString()}",
                edtStatus.text.toString()
            )
            viewModel.incluir(contato)
        }

        return root
    }


}