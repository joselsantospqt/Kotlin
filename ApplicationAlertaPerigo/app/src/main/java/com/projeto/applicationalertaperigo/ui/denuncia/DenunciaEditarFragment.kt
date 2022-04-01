package com.projeto.applicationalertaperigo.ui.denuncia

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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.projeto.applicationalertaperigo.R
import com.projeto.applicationalertaperigo.databinding.FragmentDenunciaEditarBinding
import com.projeto.applicationalertaperigo.databinding.FragmentDenunciaExcluirBinding
import com.projeto.applicationalertaperigo.model.denuncia.DadosDenuncia
import com.projeto.applicationalertaperigo.model.denuncia.DadosDenunciaComFoto
import com.projeto.applicationalertaperigo.viewModel.DenunciaViewModel
import com.projeto.applicationalertaperigo.viewModel.HomeViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_home.*

class DenunciaEditarFragment : Fragment() {

    private var _binding: FragmentDenunciaEditarBinding? = null
    private val binding get() = _binding!!
    private val nomeCollection = "Denuncias"
    private lateinit var auth: FirebaseAuth
    private var denuncia: DadosDenunciaComFoto? = null
    private val cargaViewModel: DenunciaViewModel by activityViewModels()
    private val viewModel: HomeViewModel by activityViewModels()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDenunciaEditarBinding.inflate(inflater, container, false)
        denuncia = cargaViewModel.itemDenuncia.value
        val view = binding.root
        setup(view)

        return view
    }

    private fun setup(view: View) {
        setupObservers(view)
        setupListerner(view)
    }

    private fun setupListerner(view: View) {
        val db = Firebase.firestore

        binding.btnVoltar.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_denunciaEditarFragment_to_denunciaListarFragment)
        }

        binding.btnUpdate.setOnClickListener {
            val progressDialog = ProgressDialog(this.context)
            progressDialog.setMessage("Atualizando Dados...")
            progressDialog.setCancelable(false)
            progressDialog.show()

            var updateProduto = db.collection(nomeCollection).document(denuncia?.id!!)

            db.runTransaction { transaction ->
                var newProduto = DadosDenuncia(
                    dateRegistro = binding.inputDataHora.text.toString(),
                    latitude = binding.inputLatitude.text.toString(),
                    longitude = binding.inputLongitude.text.toString(),
                    descricao = binding.inputDescricao.text.toString(),
                    titulo = binding.inputTitulo.text.toString(),
                    idUsuario = auth.currentUser?.uid.toString()
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
            Toast.makeText(context, "Concluido", Toast.LENGTH_LONG).show()
        }
    }

    private fun setupObservers(view: View) {
        cargaViewModel.itemDenuncia.observe(viewLifecycleOwner, {
            if (it != null) {
                binding.inputTitulo.setText(it.titulo)
                binding.inputDataHora.setText(it.dateRegistro)
                binding.inputDescricao.setText(it.descricao)
                binding.inputLongitude.setText(it.longitude)
                binding.inputLatitude.setText(it.latitude)
            }else{
                Toast.makeText(context, "Houve um erro no processo, tente novamente", Toast.LENGTH_LONG).show()
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