package com.example.applicationestoque.ui

import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class CadastroFragment : Fragment() {

    private var _binding: FragmentCadastroBinding? = null
    private val binding get() = _binding!!
    private var fotoTirada = false
    private val REQUEST_CODE_PHOTO = 1
    lateinit var bmp: Bitmap
    private val nomeCollection = "produtos"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCadastroBinding.inflate(inflater, container, false)
        val view = binding.root

        setupListerner(view)
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_PHOTO) {
            bmp = data?.extras?.get("data") as Bitmap
            fotoTirada = true
            binding.imageView.setImageBitmap(bmp)
        } else {
            Toast.makeText(this.context, "Erro ao bater foto", Toast.LENGTH_LONG).show()
        }
    }

    fun setupListerner(view: View) {
        binding.btnVoltar.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_cadastroFragment_to_homeFragment)
        }
        binding.btnRegistrar.setOnClickListener {
            addProduto()
        }
        binding.btnFoto.setOnClickListener {
            tirarFoto()
        }

    }

    fun addProduto() {
        if (verificaDados()) {
            var novoProduto = Produto(
                nome = binding.inputNome.text.toString(),
                quantidade = binding.inputQuantidade.text.toString().toInt(),
                preco = binding.inputPreco.text.toString().toDouble(),
                descricao = binding.inputDescricao.text.toString(),
            )

            val db = Firebase.firestore

            db.collection(nomeCollection)
                .add(novoProduto).addOnSuccessListener { documentReference ->
                    uploadFoto(documentReference.id, nomeCollection)
                    Toast.makeText(context, "Dados cadastrado com sucesso", Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener { e ->
                    Log.i(TAG, "Error ao Cadastar", e)
                }
        }
    }

    fun verificaDados(): Boolean {
        if (
            binding.inputNome.length() < 3 ||
            binding.inputDescricao.length() < 3 ||
            binding.inputPreco.length() < 2 ||
            binding.inputQuantidade.length() < 1 ||
            !fotoTirada
        ) {
            Toast.makeText(this.context, "Dados não preenchidos", Toast.LENGTH_LONG).show()
            return false
        } else {
            return true
        }
    }

    fun limparCampos() {
        binding.inputNome.setText("")
        binding.inputQuantidade.setText("")
        binding.inputPreco.setText("")
        binding.inputDescricao.setText("")
        binding.imageView.setImageResource(R.drawable.ic_person)
    }

    fun tirarFoto() {
        val i = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(i, REQUEST_CODE_PHOTO)
    }

    fun uploadFoto(idUpload: String, caminho: String) {

        val storage = FirebaseStorage.getInstance()
        var storageRef = storage.reference
        val fotoRef = storageRef.child("${caminho}/${idUpload}.jpg")
        val baos = ByteArrayOutputStream()
        bmp?.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val uploadTask = fotoRef.putBytes(baos.toByteArray())
        uploadTask.addOnSuccessListener {
            Toast.makeText(context, "Foto Enviada com sucesso", Toast.LENGTH_LONG).show()
            Log.i(TAG, "DocumentSnapshot and Register added with ID: ${idUpload}")
            limparCampos()
        }
            .addOnFailureListener { e ->
                Log.i(TAG, "Falha ao carregar a imagem", e)
            }

    }

}