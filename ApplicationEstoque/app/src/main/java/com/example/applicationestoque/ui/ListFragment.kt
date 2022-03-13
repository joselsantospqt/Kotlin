package com.example.applicationestoque.ui

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.applicationestoque.ProdutoAdapter
import com.example.applicationestoque.R
import com.example.applicationestoque.databinding.FragmentListBinding
import com.example.applicationestoque.model.Produto
import com.example.applicationestoque.model.ProdutoComFoto
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage


class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    lateinit var adapter: ProdutoAdapter
    private val nomeCollection = "produtos"


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
        setupRecyclerView(view)
    }

    fun setupListerner(view: View) {
        binding.btnVoltar.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_listFragment_to_homeFragment)
        }
    }

    fun setupRecyclerView(view: View) {
        val db = Firebase.firestore
        val storage = FirebaseStorage.getInstance()
        var storageRef = storage.reference
        var listaProduto: MutableList<ProdutoComFoto> = mutableListOf<ProdutoComFoto>()
        val listaRecebida = db.collection(nomeCollection).get().addOnSuccessListener { it ->
            for (document in it) {
                val fotoRef = storageRef.child("${nomeCollection}/${document.id}.jpg")
                fotoRef.getBytes(1024 * 1024).addOnSuccessListener {
                    val bmp = BitmapFactory.decodeByteArray(it, 0, it.size)
                    var entrada = ProdutoComFoto(
                        nome = document.get("nome").toString(),
                        quantidade = document.get("quantidade").toString().toInt(),
                        preco = document.get("preço").toString().toDouble(),
                        descricao = document.get("descricao").toString(),
                        foto = bmp
                    )
                    listaProduto.add(entrada)
                    Log.i("Download", "Tamanho da lista: ${listaProduto.size}")
                }
            }
        }

        adapter = ProdutoAdapter(listaProduto)
        binding.recyclerViewlistProduto.adapter = adapter

    }
}