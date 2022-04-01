package com.projeto.applicationalertaperigo.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.projeto.applicationalertaperigo.R
import com.projeto.applicationalertaperigo.databinding.FragmentLoginCadastrarPessoaBinding
import com.projeto.applicationalertaperigo.viewModel.LoginViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginCadastrarPessoaFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private var _binding: FragmentLoginCadastrarPessoaBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginCadastrarPessoaBinding.inflate(inflater, container, false)
        val view = binding.root
        lerViewModel()
        setup(view)
        return view
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setup(view: View) {
        setuptextChange()
        setupButton(view)
    }

    private fun setupButton(view: View) {
        binding.btnAvancar.setOnClickListener {
            if (viewModel.verificacaoDadosPessoa())
                Navigation.findNavController(view)
                    .navigate(R.id.action_loginCadastrarPessoaFragment_to_loginCadastrarEnderecoFragment)
            else
                Toast.makeText(this.context, "Preencha os campos", Toast.LENGTH_SHORT).show()
        }
        binding.btnVoltar.setOnClickListener {
            var intent = Intent(this.context, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setuptextChange() {
        binding.textInputNome.doAfterTextChanged { it ->
            if (!it.isNullOrEmpty() && !it.isBlank()) {
                viewModel.setNome(it.toString())
            } else {
                viewModel.setNome("")
                Log.i("Nome", "Vazio")
            }
        }

        binding.textInputSobrenome.doAfterTextChanged { it ->
            if (!it.isNullOrEmpty() && !it.isBlank()) {
                viewModel.setSobreNome(it.toString())
            } else {
                viewModel.setSobreNome("")
                Log.i("Sobrenome", "Vazio")
            }
        }

        binding.textInputEmail.doAfterTextChanged { it ->
            if (!it.isNullOrEmpty() && !it.isBlank()) {
                viewModel.setEmail(it.toString())
            } else {
                viewModel.setEmail("")
                Log.i("Email", "Vazio")
            }
        }

        binding.textInputSenha.doAfterTextChanged { it ->
            if (!it.isNullOrEmpty() && !it.isBlank()) {
                viewModel.setSenha(it.toString())
            } else {
                viewModel.setSenha("")
                Log.i("Senha", "Vazio")
            }
        }

        binding.textInputConfirmaSenha.doAfterTextChanged { it ->
            if (!it.isNullOrEmpty() && !it.isBlank()) {
                viewModel.setConfirmaSenha(it.toString())
            } else {
                viewModel.setConfirmaSenha("")
                Log.i("Confirmar Senha", "Vazio")
            }
        }
    }

    private fun lerViewModel() {
        binding.textInputNome.setText(viewModel.nome.value.toString())
        binding.textInputSobrenome.setText(viewModel.sobreNome.value.toString())
        binding.textInputEmail.setText(viewModel.email.value.toString())
        binding.textInputSenha.setText(viewModel.senha.value.toString())
        binding.textInputConfirmaSenha.setText(viewModel.confirmaSenha.value.toString())
    }

}