package com.example.applicationalertaperigo.ui.denuncia

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.applicationalertaperigo.R
import com.example.applicationalertaperigo.databinding.FragmentDenunciaExcluirBinding
import com.example.applicationalertaperigo.model.denuncia.DadosDenunciaComFoto
import com.example.applicationalertaperigo.viewModel.DenunciaViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class DenunciaExcluirFragment : Fragment() {

    private var _binding: FragmentDenunciaExcluirBinding? = null
    private val binding get() = _binding!!
    private val nomeCollection = "Denuncias"
    private var denuncia: DadosDenunciaComFoto? = null
    private val cargaViewModel: DenunciaViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDenunciaExcluirBinding.inflate(inflater, container, false)
        val view = binding.root
        setup(view)

        return view
    }

    private fun setup(view: View) {
        CarregaViewModel()
        setupListerner(view)
        carregaDenuncia()
    }

    private fun carregaDenuncia() {
        denuncia = cargaViewModel.itemDenuncia.value
        if (denuncia == null) {

        }
        binding.inputDataHora.setText(denuncia?.dateRegistro)
        binding.inputDescricao.setText(denuncia?.descricao)
        binding.inputLatitude.setText(denuncia?.latitude)
        binding.inputLongitude.setText(denuncia?.longitude)
    }

    fun limparCampos() {
        binding.inputDataHora.setText("")
        binding.inputLatitude.setText("")
        binding.inputLongitude.setText("")
        binding.inputDescricao.setText("")
        binding.imageView.setImageResource(R.drawable.ic_person)
    }

    private fun setupListerner(view: View) {
        val db = Firebase.firestore
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference

        binding.btnVoltar.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_denunciaExcluirFragment_to_denunciaListarFragment)
        }
        binding.btnDeletar.setOnClickListener {
            Log.i("delete", "Produto apagado: ID: ${denuncia?.id}")
            val progressDialog = ProgressDialog(this.context)
            progressDialog.setMessage("Excluido Dados...")
            progressDialog.setCancelable(false)
            progressDialog.show()

            if (denuncia?.id != null) {
                db.collection(nomeCollection).document(denuncia?.id!!)
                    .delete()
                    .addOnSuccessListener {
                        val fotoRef =
                            storageRef.child("$nomeCollection/${denuncia?.id}.jpg")
                        fotoRef.delete().addOnSuccessListener {
                            if (progressDialog.isShowing)
                                progressDialog.dismiss()
                            Log.i("delete", "DocumentSnapshot successfully deleted!")
                            Navigation.findNavController(view)
                                .navigate(R.id.action_denunciaExcluirFragment_to_denunciaListarFragment)
                        }.addOnFailureListener {
                            progressDialog.dismiss()
                            Log.i("delete", "Uh-oh, an error occurred !")
                        }
                    }
                    .addOnFailureListener { e ->
                        Log.i("delete", "Error deleting document", e)
                        progressDialog.dismiss()
                    }
                limparCampos()
            } else
                Toast.makeText(context, "Produto não encontrado", Toast.LENGTH_LONG).show()

        }
    }

    private fun CarregaViewModel() {
        cargaViewModel.itemDenuncia.observe(viewLifecycleOwner, {
            if (it != null) {
                binding.inputDataHora.setText(it.dateRegistro)
                binding.inputDescricao.setText(it.descricao)
                binding.inputLongitude.setText(it.longitude)
                binding.inputLatitude.setText(it.latitude)
            }
        })
    }

}