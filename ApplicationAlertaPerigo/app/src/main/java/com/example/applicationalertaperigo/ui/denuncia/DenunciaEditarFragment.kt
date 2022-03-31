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
import com.example.applicationalertaperigo.databinding.FragmentDenunciaEditarBinding
import com.example.applicationalertaperigo.databinding.FragmentDenunciaExcluirBinding
import com.example.applicationalertaperigo.model.denuncia.DadosDenuncia
import com.example.applicationalertaperigo.model.denuncia.DadosDenunciaComFoto
import com.example.applicationalertaperigo.viewModel.DenunciaViewModel
import com.example.applicationalertaperigo.viewModel.HomeViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class DenunciaEditarFragment : Fragment() {

    private var _binding: FragmentDenunciaEditarBinding? = null
    private val binding get() = _binding!!
    private val nomeCollection = "Denuncias"
    private var denuncia: DadosDenunciaComFoto? = null
    private val cargaViewModel: DenunciaViewModel by activityViewModels()
    private val viewModel: HomeViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDenunciaEditarBinding.inflate(inflater, container, false)
        val view = binding.root
        setup(view)

        return view
    }

    private fun setup(view: View) {
        setupObservers(view)
        setupListerner(view)
        carregaDenuncia()
    }

    private fun setupListerner(view: View) {
        val db = Firebase.firestore

        binding.btnVoltar.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_denunciaEditarFragment_to_denunciaListarFragment)
        }

        binding.btnUpdate.setOnClickListener {
            val progressDialog = ProgressDialog(this.context)
            progressDialog.setMessage("Excluido Dados...")
            progressDialog.setCancelable(false)
            progressDialog.show()

            var updateProduto = db.collection(nomeCollection).document(denuncia?.id!!)

            db.runTransaction { transaction ->
                var newProduto = DadosDenuncia(
                    dateRegistro = binding.inputDataHora.text.toString(),
                    latitude = binding.inputLatitude.text.toString(),
                    longitude = binding.inputLongitude.text.toString(),
                    descricao = binding.inputDescricao.text.toString(),
                )
                transaction.update(updateProduto, newProduto.toMap())

                // Success
                null
            }.addOnSuccessListener {
                if (progressDialog.isShowing)
                    progressDialog.dismiss()
                Navigation.findNavController(view)
                    .navigate(R.id.action_denunciaEditarFragment_to_denunciaListarFragment)
            }
                .addOnFailureListener {
                    progressDialog.dismiss()
                }
        }
        Toast.makeText(context, "Concluido", Toast.LENGTH_LONG).show()
    }

    private fun carregaDenuncia() {
        denuncia = cargaViewModel.itemDenuncia.value
        if (denuncia == null) {
            Toast.makeText(context, "Houve um erro no processo, tente novamente", Toast.LENGTH_LONG).show()
        } else {
            binding.inputDataHora.setText(denuncia?.dateRegistro)
            binding.inputDescricao.setText(denuncia?.descricao)
            binding.inputLatitude.setText(denuncia?.latitude)
            binding.inputLongitude.setText(denuncia?.longitude)
        }
    }

    private fun setupObservers(view: View) {
        cargaViewModel.itemDenuncia.observe(viewLifecycleOwner, {
            if (it != null) {
                binding.inputDataHora.setText(it.dateRegistro)
                binding.inputDescricao.setText(it.descricao)
                binding.inputLongitude.setText(it.longitude)
                binding.inputLatitude.setText(it.latitude)
            }
        })

        viewModel.trocaFragment.observe(viewLifecycleOwner, {
            if (it != null) {
                when (it) {
                    1 -> {
                        viewModel.NavegaFragment(0)
                        Navigation.findNavController(view)
                            .navigate(R.id.action_denunciaEditarFragment_to_homeDashboardFragment)
                    }
                    2 -> {
                        viewModel.NavegaFragment(0)
                        Navigation.findNavController(view)
                            .navigate(R.id.action_denunciaEditarFragment_to_homePerfilFragment)
                    }
                    3 -> {
                        viewModel.NavegaFragment(0)
                        Navigation.findNavController(view)
                            .navigate(R.id.action_denunciaEditarFragment_to_denunciaCadastroFragment)
                    }
                    4 -> {
                        viewModel.NavegaFragment(0)
                        Navigation.findNavController(view)
                            .navigate(R.id.action_denunciaEditarFragment_to_denunciaListarFragment)
                    }
                }

            }
        })
    }

}