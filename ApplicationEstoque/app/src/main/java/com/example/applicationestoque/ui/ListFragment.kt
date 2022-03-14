package com.example.applicationestoque.ui

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.applicationestoque.ProdutoAdapter
import com.example.applicationestoque.R
import com.example.applicationestoque.databinding.FragmentListBinding
import com.example.applicationestoque.model.Produto
import com.example.applicationestoque.model.ProdutoComFoto
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private val nomeCollection = "produtos"
    lateinit var adapter: ProdutoAdapter
    lateinit var listaFora: List<Produto>


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
        var storageRef = storage.reference
        var listaProduto: MutableList<Produto> = mutableListOf<Produto>()
        val listaRecebida = db
            .collection(nomeCollection)
            .orderBy("nome")
//            .whereGreaterThan("preco", "0")
            .get()
            .addOnSuccessListener { it ->
                for (document in it) {
                    var entrada = Produto(
                        nome = document.get("nome").toString(),
                        quantidade = document.get("quantidade").toString().toInt(),
                        preco = document.get("preco").toString().toDouble(),
                        descricao = document.get("descricao").toString()
                    )
                    listaProduto.add(entrada)
                }
                listaFora = listaProduto
                Log.i("Download", "listaFora tem tamanho: ${listaFora.size}")
                Log.i("Download", "lista de Produto tem tamanho: ${listaProduto.size}")

                adapter = ProdutoAdapter {
                    Toast.makeText(context, "Cliquei no item: ${it.nome}", Toast.LENGTH_LONG).show()
                }

                binding.recyclerViewlistProduto.layoutManager = LinearLayoutManager(this.context)
                binding.recyclerViewlistProduto.adapter = adapter

                adapter.submitList(listaProduto)

            }.addOnFailureListener {
                Toast.makeText(context, "Ocorreu um Erro ao baixar a lista", Toast.LENGTH_LONG)
                    .show()

            }
    }
}