package com.example.applicationestoque.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.applicationestoque.R
import com.example.applicationestoque.databinding.FragmentDeletarBinding
import com.example.applicationestoque.databinding.FragmentEditarBinding
import com.example.applicationestoque.model.Produto
import com.example.applicationestoque.model.ProdutoComFoto
import com.example.applicationestoque.model.ProdutoViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EditarFragment : Fragment() {

    private var _binding: FragmentEditarBinding? = null
    private val binding get() = _binding!!
    private val nomeCollection = "produtos"
    private var produto: ProdutoComFoto? = null
    private val cargaViewModel: ProdutoViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEditarBinding.inflate(inflater, container, false)
        val view = binding.root
        setup(view)

        return view
    }

    private fun setup(view: View) {
        CarregaViewModel()
        setupListerner(view)
        carregaProduto()
    }

    private fun carregaProduto() {
        produto = cargaViewModel.itemProduto.value
        if (produto == null) {

        }

        Log.i("Teste", "Testeando Produto: ${produto?.nome}")
        binding.inputNome.setText(produto?.nome)
        binding.inputDescricao.setText(produto?.descricao)
        binding.inputPreco.setText(produto?.preco.toString())
        binding.inputQuantidade.setText(produto?.quantidade.toString())
    }

    private fun setupListerner(view: View) {
        val db = Firebase.firestore

        binding.btnVoltar.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_editarFragment_to_listFragment)
        }
        binding.btnUpdate.setOnClickListener {
            Log.i("delete", "Produto Atualizado: ID: ${produto?.id}")

            var updateProduto =  db.collection(nomeCollection).document(produto?.id!!)

            db.runTransaction { transaction ->
                var newProduto = Produto(
                    nome = binding.inputNome.text.toString(),
                    quantidade = binding.inputQuantidade.text.toString().toInt(),
                    preco = binding.inputPreco.text.toString().toDouble(),
                    descricao = binding.inputDescricao.text.toString(),
                )

                transaction.update(updateProduto, newProduto.toMap())

                // Success
                null
            }.addOnSuccessListener {
                Toast.makeText(context, "Produto atualizado", Toast.LENGTH_LONG).show()
                Navigation.findNavController(view).navigate(R.id.action_editarFragment_to_listFragment)
            }
                .addOnFailureListener { Toast.makeText(context, "Produto não atualizado", Toast.LENGTH_LONG).show() }
        }
    }

    fun limparCampos() {
        binding.inputNome.setText("")
        binding.inputQuantidade.setText("")
        binding.inputPreco.setText("")
        binding.inputDescricao.setText("")
        binding.imageView.setImageResource(R.drawable.ic_person)
    }
}