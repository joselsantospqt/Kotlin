package com.example.applicationestoque.ui

import android.content.ContentValues.TAG
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.applicationestoque.ProdutoAdapter
import com.example.applicationestoque.R
import com.example.applicationestoque.databinding.FragmentListBinding
import com.example.applicationestoque.model.ProdutoViewModel
import com.example.applicationestoque.model.Produto
import com.example.applicationestoque.model.ProdutoComFoto
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.google.firebase.firestore.DocumentChange

class ListFragment : Fragment(),  DialogInterface.OnClickListener  {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private val nomeCollection = "produtos"
    lateinit var adapter: ProdutoAdapter
    lateinit var listaFora: List<ProdutoComFoto>
    private val cargaViewModel: ProdutoViewModel by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch() {
            delay(2000)
            adapter.submitList(listaFora)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentListBinding.inflate(inflater, container, false)
        val view = binding.root
        setup(view)

        return view
    }

    fun setup(view: View) {
        setupListerner(view)
        setupRecyclerView()
    }

    fun setupListerner(view: View) {
        binding.btnVoltar.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_listFragment_to_homeFragment)
        }
    }

    fun setupRecyclerView() {
        val db = Firebase.firestore
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference
        val listaProduto: MutableList<ProdutoComFoto> = mutableListOf()
        val listaTempoReal = db
            .collection(nomeCollection)
            .orderBy("nome")
            .addSnapshotListener { it, e ->
                if (e != null) {
                    Log.i(TAG, "Erro ao carregar as informações", e)
                    return@addSnapshotListener
                }
//                listaProduto.clear()

                if (it != null) {
                    for (document in it.documentChanges) {

                        when (document.type) {
                            DocumentChange.Type.ADDED -> {
                                Log.d(
                                    "Download",
                                    "Novo produto ${document.document.data.get("nome")}"
                                )
                                val entrada = ProdutoComFoto(
                                    id = document.document.id,
                                    nome = document.document.data.get("nome").toString(),
                                    quantidade = document.document.data.get("quantidade").toString()
                                        .toInt(),
                                    preco = document.document.data.get("preco").toString()
                                        .toDouble(),
                                    descricao = document.document.data.get("descricao").toString()
                                )
                                listaProduto.add(entrada)
                            }
                            DocumentChange.Type.MODIFIED -> {
                                Log.d(
                                    "Download",
                                    "Produto Alterado ${document.document.data.get("nome")}"
                                )
//                                https://firebase.google.com/docs/firestore/query-data/listen?hl=pt&authuser=0
                            }
                            DocumentChange.Type.REMOVED -> Log.d(
                                "Download",
                                "Produto Removido ${document.document.data.get("nome")}"
                            )
                        }
                    }
                }
                listaFora = listaProduto
                adapter = ProdutoAdapter {
                    Toast.makeText(context, "Cliquei no item: ${it.nome}", Toast.LENGTH_LONG).show()
                    Log.i("AÇÃO DO CLICK","Testeando Produto: ${it?.nome}")

                    var produto = ProdutoComFoto(
                        id = it.id,
                        nome = it.nome,
                        quantidade = it.quantidade,
                        preco = it.preco,
                        descricao = it.descricao
                    )
                    cargaViewModel.setData(produto)



                    val alertDialog = AlertDialog.Builder(requireContext())
                    alertDialog.setTitle("Alerta")
                    alertDialog.setMessage("Opção do item : ${it.nome}")
                    alertDialog.setPositiveButton("Editar", this)
                    alertDialog.setNegativeButton("Excluir", this)
                    alertDialog.setCancelable(true)//alerta modal
                    alertDialog.show()



                }

                binding.recyclerViewlistProduto.layoutManager = LinearLayoutManager(this.context)
                binding.recyclerViewlistProduto.adapter = adapter

                adapter.submitList(listaProduto)

            }
    }

    override fun onClick(dialog: DialogInterface?, id: Int) {
        val alertDialog = dialog as AlertDialog // Cast implicito
        val rotuloBotao = alertDialog.getButton(id).text
        val navController = findNavController()
        when(rotuloBotao){
            "Editar" ->  Toast.makeText(context, "funcionalidade não foi implementada", Toast.LENGTH_SHORT).show()
            "Excluir" -> navController.navigate(R.id.action_listFragment_to_deletarFragment)
        }
    }



}