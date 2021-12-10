package com.example.applicationrecycloview

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
import com.example.applicationrecycloview.dados.AgendaDB
import com.example.applicationrecycloview.dados.Contato
import com.example.applicationrecycloview.listas.ContatoAdapter

class ListaPessoas : Fragment() {
    
    private lateinit var viewModel: ListaPessoasViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.lista_pessoas_fragment, container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = AgendaDB.getInstance(application).contatoDao
        val factory = ListaPessoasViewModelFactory(dataSource, application)
        viewModel = ViewModelProvider(this, factory).get(ListaPessoasViewModel::class.java)

        val adapter = ContatoAdapter()
        val lista = root.findViewById<RecyclerView>(R.id.lstPessoas)
        lista.layoutManager = LinearLayoutManager(application)
        lista.adapter = adapter
        viewModel.contatos.observe(viewLifecycleOwner, Observer{
            it?.let{
                adapter.dados = it
            }
        })

        val edtNome = root.findViewById<EditText>(R.id.edtNome)
        val btnIncluir = root.findViewById<Button>(R.id.btnIncluir)

        btnIncluir.setOnClickListener {
            val nome = edtNome.text.toString()
            val contato = Contato(0L, nome, "111111", "$nome@Gmail.com")
            viewModel.incluir(contato)
        }

        return root
    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(ListaPessoasViewModel::class.java)
//        // TODO: Use the ViewModel
//    }

}