package com.example.applicationalertaperigo.ui.login

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
import com.example.applicationalertaperigo.R
import com.example.applicationalertaperigo.databinding.FragmentLoginCadastrarEnderecoBinding
import com.example.applicationalertaperigo.viewModel.LoginViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginCadastrarEnderecoFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private var _binding: FragmentLoginCadastrarEnderecoBinding? = null
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
        _binding = FragmentLoginCadastrarEnderecoBinding.inflate(inflater, container, false)
        val view = binding.root
        lerViewModel()
        setup(view)
        return view
    }

    private fun setup(view: View) {
        lerViewModel()
        setuptextChange()
        setupButton(view)
//        setupObservers()
    }

    private fun setupButton(view: View) {
        binding.btnAvancar.setOnClickListener {
            if (viewModel.verificacaoDadosEndereco())
                cadastroFireBase()
            else {
                Toast.makeText(activity, "Preencha os campos", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnVoltar.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_loginCadastrarEnderecoFragment_to_loginCadastrarPessoaFragment)
        }
    }

    private fun cadastroFireBase() {
        viewModel.cadastroFireBase()
        viewModel.confirmaCadastro.observe(viewLifecycleOwner) {
            if (it == true) {
                var intent = Intent(activity, LoginActivity::class.java)
                startActivity(intent)
            } else
                Toast.makeText(activity, "Processando...", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setuptextChange() {
        binding.textInputCEP.doAfterTextChanged { it ->
            if (!it.isNullOrEmpty() && !it.isBlank() && it?.length == 8) {
                viewModel.setCEP(it.toString())
            } else {
                viewModel.setCEP("")
                Log.i("CEP", "CEP menor que 8 caracteres")
            }
        }

        binding.textInputEstado.doAfterTextChanged { it ->
            if (!it.isNullOrEmpty() && !it.isBlank()) {
                viewModel.setEstado(it.toString())
            } else {
                viewModel.setEstado("")
                Log.i("Estado", "Vazio")
            }
        }

        binding.textInputCidade.doAfterTextChanged { it ->
            if (!it.isNullOrEmpty() && !it.isBlank()) {
                viewModel.setCidade(it.toString())
            } else {
                viewModel.setCidade("")
                Log.i("Cidade", "Vazio")
            }
        }

        binding.textInputBairro.doAfterTextChanged { it ->
            if (!it.isNullOrEmpty() && !it.isBlank()) {
                viewModel.setBairro(it.toString())
            } else {
                viewModel.setBairro("")
                Log.i("Bairro", "Vazio")
            }
        }

        binding.textInputLogradouro.doAfterTextChanged { it ->
            if (!it.isNullOrEmpty() && !it.isBlank()) {
                viewModel.setLogradouro(it.toString())
            } else {
                viewModel.setLogradouro("")
                Log.i("Logradouro", "Vazio")
            }
        }


        binding.textInputNumero.doAfterTextChanged { it ->
            if (!it.isNullOrEmpty() && !it.isBlank()) {
                viewModel.setNumero(it.toString())
            } else {
                viewModel.setNumero("")
                Log.i("Numero", "Vazio")
            }
        }

        binding.textInputComplemento.doAfterTextChanged { it ->
            if (!it.isNullOrEmpty() && !it.isBlank()) {
                viewModel.setComplemento(it.toString())
            } else {
                viewModel.setComplemento("")
                Log.i("Complemento", "Vazio")
            }
        }
    }

    private fun lerViewModelSemCep() {
        binding.textInputCEP.setText(viewModel.cep.value.toString())
        binding.textInputBairro.setText(viewModel.bairro.value.toString())
        binding.textInputEstado.setText(viewModel.estado.value.toString())
        binding.textInputComplemento.setText(viewModel.complemento.value.toString())
        binding.textInputNumero.setText(viewModel.numero.value.toString())
        binding.textInputLogradouro.setText(viewModel.logradouro.value.toString())
        binding.textInputCidade.setText(viewModel.cidade.value.toString())
    }

    private fun lerViewModel() {
        binding.textInputCEP.setText(viewModel.cep.value.toString())
        binding.textInputBairro.setText(viewModel.bairro.value.toString())
        binding.textInputEstado.setText(viewModel.estado.value.toString())
        binding.textInputComplemento.setText(viewModel.complemento.value.toString())
        binding.textInputNumero.setText(viewModel.numero.value.toString())
        binding.textInputLogradouro.setText(viewModel.logradouro.value.toString())
        binding.textInputCidade.setText(viewModel.cidade.value.toString())
    }

    private fun setupObservers() {
        viewModel.cep.observe(viewLifecycleOwner) {
            if (it.length == 8) {
                viewModel.getEndereco()
            }
        }
        viewModel.atualizaEndereco.observe(viewLifecycleOwner) {
            lerViewModelSemCep()
        }
    }


}