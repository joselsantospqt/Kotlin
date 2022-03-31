package com.example.applicationalertaperigo.ui.home

import android.content.Intent
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
import com.example.applicationalertaperigo.databinding.FragmentHomeDashboardBinding
import com.example.applicationalertaperigo.databinding.FragmentHomePerfilBinding
import com.example.applicationalertaperigo.model.login.DadosPessoa
import com.example.applicationalertaperigo.ui.login.LoginActivity
import com.example.applicationalertaperigo.viewModel.HomeViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class HomePerfilFragment : Fragment() {

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
        val db = Firebase.firestore

        binding.btnExcluir.setOnClickListener {
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

        binding.btnUpdate.setOnClickListener {
            verificaLogado()
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
                            "Usuario não atualizado",
                            Toast.LENGTH_LONG
                        ).show()
                    }
            }
        }

        binding.btnVoltar.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_homePerfilFragment_to_homeDashboardFragment)
        }
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
            Toast.makeText(this.context, "Dados não preenchidos", Toast.LENGTH_LONG).show()
            return false
        } else {
            return true
        }
    }

    private fun verificaLogado() {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            var intent = Intent(this.context, LoginActivity::class.java)
            startActivity(intent)
            Toast.makeText(this.context, "Conexão Perdida", Toast.LENGTH_LONG).show()
        }
    }

}