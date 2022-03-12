package com.example.applicationestoque.ui

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.applicationestoque.R
import com.example.applicationestoque.databinding.FragmentCadastroBinding
import com.example.applicationestoque.databinding.FragmentHomeBinding
import com.example.applicationestoque.model.Produto
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class CadastroFragment : Fragment() {

    private var _binding: FragmentCadastroBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCadastroBinding.inflate(inflater, container, false)
        val view = binding.root

        setupListerner(view)
        return view
    }

    fun setupListerner(view: View) {
        binding.btnVoltar.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_cadastroFragment_to_homeFragment)
        }

        binding.btnRegistrar.setOnClickListener {
            addProduto()
        }

    }

    fun addProduto() {
        if (verificaDados()) {
            var novoProduto = Produto(
                nome = binding.inputNome.text.toString(),
                quantidade = binding.inputQuantidade.text.toString().toInt(),
                preco = binding.inputPreco.text.toString().toDouble(),
                descricao = binding.inputDescricao.text.toString(),
                uriFoto = null
            )

            val db = Firebase.firestore

            db.collection("produtos")
                .add(novoProduto)
                .addOnSuccessListener { documentReference ->
                    Log.i(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.i(TAG, "Error adding document", e)
                }
        }
    }

    fun verificaDados(): Boolean {
        if (
            binding.inputNome.length() < 3 ||
            binding.inputDescricao.length() < 3 ||
            binding.inputPreco.length() < 4 ||
            binding.inputQuantidade.length() < 1
        ) {
            Toast.makeText(this.context, "Dados não preenchidos", Toast.LENGTH_LONG).show()
            return false
        } else {
            return true
        }
    }


}