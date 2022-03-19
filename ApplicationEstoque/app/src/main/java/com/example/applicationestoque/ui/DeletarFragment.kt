package com.example.applicationestoque.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.applicationestoque.R
import com.example.applicationestoque.databinding.FragmentDeletarBinding
import com.example.applicationestoque.model.ProdutoComFoto
import com.example.applicationestoque.model.ProdutoViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class DeletarFragment : Fragment() {

    private var _binding: FragmentDeletarBinding? = null
    private val binding get() = _binding!!
    private val nomeCollection = "produtos"
    private var produto: ProdutoComFoto? = null
    private val cargaViewModel: ProdutoViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDeletarBinding.inflate(inflater, container, false)
        val view = binding.root
        setup(view)

        return view
    }

    fun setup(view: View) {
        CarregaViewModel()
        setupListerner(view)
        carregaProduto()
    }

    fun carregaProduto() {

        produto = cargaViewModel.itemProduto.value
        if (produto == null) {

        }

        Log.i("Teste", "Testeando Produto: ${produto?.nome}")
        binding.inputNome.setText(produto?.nome)
        binding.inputDescricao.setText(produto?.descricao)
        binding.inputPreco.setText(produto?.preco.toString())
        binding.inputQuantidade.setText(produto?.quantidade.toString())
    }

    fun limparCampos() {
        binding.inputNome.setText("")
        binding.inputQuantidade.setText("")
        binding.inputPreco.setText("")
        binding.inputDescricao.setText("")
        binding.imageView.setImageResource(R.drawable.ic_person)
    }

    fun setupListerner(view: View) {
        val db = Firebase.firestore

        binding.btnVoltar.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_deletarFragment_to_listFragment)
        }
        binding.btnDeletar.setOnClickListener {
            Log.i("delete", "Produto apagado: ID: ${produto?.id}")

            if (produto?.id != null) {
                db.collection(nomeCollection).document(produto?.id!!)
                    .delete()
                    .addOnSuccessListener { Log.i("delete", "DocumentSnapshot successfully deleted!") }
                    .addOnFailureListener { e -> Log.i("delete", "Error deleting document", e) }
                limparCampos()
            } else
                Toast.makeText(context, "Produto não encontrado", Toast.LENGTH_LONG).show()

        }
    }

    fun CarregaViewModel() {

        cargaViewModel.itemProduto.observe(viewLifecycleOwner, {
            if (it != null) {
                binding.inputNome.setText(it.nome)
                binding.inputDescricao.setText(it.descricao)
                binding.inputPreco.setText(it.preco.toString())
                binding.inputQuantidade.setText(it.quantidade.toString())
            }
        })
    }


}