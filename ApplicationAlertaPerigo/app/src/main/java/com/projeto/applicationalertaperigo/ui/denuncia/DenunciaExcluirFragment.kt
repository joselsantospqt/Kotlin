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
import com.projeto.applicationalertaperigo.R
import com.projeto.applicationalertaperigo.databinding.FragmentDenunciaExcluirBinding
import com.projeto.applicationalertaperigo.model.denuncia.DadosDenunciaComFoto
import com.projeto.applicationalertaperigo.viewModel.DenunciaViewModel
import com.projeto.applicationalertaperigo.viewModel.HomeViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.coroutines.Dispatchers.Unconfined
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DenunciaExcluirFragment : Fragment() {

    private var _binding: FragmentDenunciaExcluirBinding? = null
    private val binding get() = _binding!!
    private val nomeCollection = "Denuncias"
    private var denuncia: DadosDenunciaComFoto? = null
    private val cargaViewModel: DenunciaViewModel by activityViewModels()
    private val viewModel: HomeViewModel by activityViewModels()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDenunciaExcluirBinding.inflate(inflater, container, false)
        denuncia = cargaViewModel.itemDenuncia.value
        val view = binding.root
        setup(view)

        return view
    }

    private fun setup(view: View) {
        setupObservers(view)
        setupListerner(view)
    }


    private fun limparCampos() {
        binding.inputDataHora.setText("")
        binding.inputLatitude.setText("")
        binding.inputLongitude.setText("")
        binding.inputDescricao.setText("")
        binding.inputTitulo.setText("")
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

    private fun setupObservers(view: View) {
        cargaViewModel.itemDenuncia.observe(viewLifecycleOwner, {
            if (it != null) {
                binding.inputDataHora.setText(it.dateRegistro)
                binding.inputDescricao.setText(it.descricao)
                binding.inputLongitude.setText(it.longitude)
                binding.inputLatitude.setText(it.latitude)
                binding.inputTitulo.setText(it.titulo)
            } else {
                Toast.makeText(
                    context,
                    "Houve um erro no processo, tente novamente",
                    Toast.LENGTH_LONG
                ).show()
            }
        })

        viewModel.trocaFragment.observe(viewLifecycleOwner, {
            if (it != null) {
                when (it) {
                    1 -> {
                        viewModel.NavegaFragment(0)
                        Navigation.findNavController(view)
                            .navigate(R.id.action_denunciaExcluirFragment_to_homeDashboardFragment)
                    }
                    2 -> {
                        viewModel.NavegaFragment(0)
                        Navigation.findNavController(view)
                            .navigate(R.id.action_denunciaExcluirFragment_to_homePerfilFragment)
                    }
                    3 -> {
                        viewModel.NavegaFragment(0)
                        Navigation.findNavController(view)
                            .navigate(R.id.action_denunciaExcluirFragment_to_denunciaCadastroFragment)
                    }
                    4 -> {
                        viewModel.NavegaFragment(0)
                        Navigation.findNavController(view)
                            .navigate(R.id.action_denunciaExcluirFragment_to_denunciaListarFragment)
                    }
                    5 -> {
                        viewModel.NavegaFragment(0)
                        Navigation.findNavController(view)
                            .navigate(R.id.action_denunciaExcluirFragment_to_directoryFragment)
                    }
                }

            }
        })
    }

}