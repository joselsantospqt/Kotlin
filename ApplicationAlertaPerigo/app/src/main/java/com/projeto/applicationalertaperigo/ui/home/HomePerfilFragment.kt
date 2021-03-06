package com.projeto.applicationalertaperigo.ui.home

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.projeto.applicationalertaperigo.R
import com.projeto.applicationalertaperigo.databinding.FragmentHomeDashboardBinding
import com.projeto.applicationalertaperigo.databinding.FragmentHomePerfilBinding
import com.projeto.applicationalertaperigo.model.login.DadosPessoa
import com.projeto.applicationalertaperigo.ui.login.LoginActivity
import com.projeto.applicationalertaperigo.viewModel.HomeViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class HomePerfilFragment : Fragment(), DialogInterface.OnClickListener {

    private var _binding: FragmentHomePerfilBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by activityViewModels()
    private lateinit var auth: FirebaseAuth
    private val nomeCollection = "Usuario"


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
        _binding = FragmentHomePerfilBinding.inflate(inflater, container, false)
        val view = binding.root
        setup(view)

        return view
    }

    private fun setup(view: View) {
        setupObservers(view)
        setuptextChange()
        setupButton(view)
    }

    private fun setuptextChange() {
    }

    private fun setupButton(view: View) {
        binding.btnExcluir.setOnClickListener {
            val alertDialog = AlertDialog.Builder(requireContext())
            alertDialog.setTitle("Alerta")
            alertDialog.setMessage("Confirma Excluir seu Perfil")
            alertDialog.setPositiveButton("Excluir", this)
            alertDialog.setNegativeButton("Cancelar", this)
            alertDialog.setCancelable(true)//alerta modal
            alertDialog.show()
        }

        binding.btnUpdate.setOnClickListener {
            UpdatePerfil(Firebase.firestore, view)
        }

        binding.btnVoltar.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_homePerfilFragment_to_homeDashboardFragment)
        }
    }

    private fun UpdatePerfil(
        db: FirebaseFirestore,
        view: View
    ) {
        if (verificaDados()) {
            var usuario =
                db.collection(nomeCollection).document(auth.currentUser?.uid.toString())
            db.runTransaction { transaction ->
                var updateUsuario = DadosPessoa(
                    nome = binding.inputNome.text.toString(),
                    sobrenome = binding.inputSobreNome.text.toString(),
                    email = binding.inputEmail.text.toString(),
                    cep = binding.inputCep.text.toString(),
                    estado = binding.inputEstado.text.toString(),
                    cidade = binding.inputCidade.text.toString(),
                    bairro = binding.inputBairro.text.toString(),
                    logradouro = binding.inputLogradouro.text.toString(),
                    numero = binding.inputNumero.text.toString(),
                    complemento = binding.inputComplemento.text.toString()
                )
                transaction.update(usuario, updateUsuario.toMap())
                // Success
                null
            }.addOnSuccessListener {
                Toast.makeText(context, "Usuario atualizado", Toast.LENGTH_LONG).show()
                Navigation.findNavController(view)
                    .navigate(R.id.action_homePerfilFragment_to_homeDashboardFragment)
            }
                .addOnFailureListener {
                    Toast.makeText(
                        context,
                        "Usuario n??o atualizado",
                        Toast.LENGTH_LONG
                    ).show()
                }
        }
    }

    private fun ExcluirPerfil(db: FirebaseFirestore) {
        if (auth.currentUser?.uid.toString() != null) {
            db.collection(nomeCollection).document(auth.currentUser?.uid.toString())
                .delete()
                .addOnSuccessListener {
                    val usuario = Firebase.auth.currentUser!!
                    usuario.delete()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(
                                    this.context,
                                    "Conta ao Excluida com sucesso !",
                                    Toast.LENGTH_LONG
                                ).show()
                                var intent = Intent(this.context, LoginActivity::class.java)
                                startActivity(intent)
                            } else
                                Toast.makeText(
                                    this.context,
                                    "${task.exception}",
                                    Toast.LENGTH_LONG
                                ).show()
                        }
                }
                .addOnFailureListener { e -> Log.i("delete", "Error deleting document", e) }
        } else
            Toast.makeText(context, "Erro ao Excluir a conta", Toast.LENGTH_LONG).show()
    }

    private fun setupObservers(view: View) {
        viewModel.dadosPessoa.observe(viewLifecycleOwner, {
            if (it != null) {
                binding.inputNome.setText(it.nome.toString())
                binding.inputSobreNome.setText(it.sobrenome.toString())
                binding.inputEmail.setText(it.email.toString())
                binding.inputCep.setText(it.cep.toString())
                binding.inputEstado.setText(it.estado.toString())
                binding.inputCidade.setText(it.cidade.toString())
                binding.inputBairro.setText(it.bairro.toString())
                binding.inputLogradouro.setText(it.logradouro.toString())
                binding.inputNumero.setText(it.numero.toString())
                binding.inputComplemento.setText(it.complemento.toString())
            }
        })

        viewModel.trocaFragment.observe(viewLifecycleOwner, {
            if (it != null) {
                when (it) {
                    1 -> {
                        viewModel.NavegaFragment(0)
                        Navigation.findNavController(view)
                            .navigate(R.id.action_homePerfilFragment_to_homeDashboardFragment)
                    }
                    3 -> {
                        viewModel.NavegaFragment(0)
                        Navigation.findNavController(view)
                            .navigate(R.id.action_homePerfilFragment_to_denunciaCadastroFragment)
                    }
                    4 -> {
                        viewModel.NavegaFragment(0)
                        Navigation.findNavController(view)
                            .navigate(R.id.action_homePerfilFragment_to_denunciaListarFragment)
                    }
                }

            }
        })
    }

    private fun verificaDados(): Boolean {
        if (
            binding.inputNome.length() < 3 ||
            binding.inputSobreNome.length() < 3
        ) {
            Toast.makeText(this.context, "Dados n??o preenchidos", Toast.LENGTH_LONG).show()
            return false
        } else {
            return true
        }
    }

    override fun onClick(dialog: DialogInterface?, id: Int) {
        val alertDialog = dialog as AlertDialog // Cast implicito
        val rotuloBotao = alertDialog.getButton(id).text
        val navController = findNavController()
        when (rotuloBotao) {
            "Cancelar" -> alertDialog.cancel()
            "Excluir" -> ExcluirPerfil(Firebase.firestore)
        }
    }

}