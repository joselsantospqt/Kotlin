package com.example.applicationloginfirebase.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.Navigator
import com.example.applicationloginfirebase.R
import com.example.applicationloginfirebase.databinding.ActivityCadastrarBinding
import com.example.applicationloginfirebase.databinding.FragmentCadastrarPessoaBinding
import com.example.applicationloginfirebase.viewModel.MainViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class CadastrarPessoaFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private var _binding: FragmentCadastrarPessoaBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCadastrarPessoaBinding.inflate(inflater, container, false)
        val view = binding.root
        lerViewModel()
        setup(view)
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun setup(view: View) {
        setuptextChange()
        setupButton(view)
    }

    fun setupButton(view: View) {
        binding.btnAvancar.setOnClickListener {
            if (viewModel.verificacaoDadosPessoa())
                Navigation.findNavController(view)
                    .navigate(R.id.action_cadastrarPessoaFragment_to_cadastarEnderecoFragment)
            else
                Toast.makeText(activity, "Preencha os campos", Toast.LENGTH_SHORT).show()
        }
        binding.btnVoltar.setOnClickListener {
            var intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    fun setuptextChange() {
        binding.textInputNome.doAfterTextChanged { it ->
            if (!it.isNullOrEmpty() && !it.isBlank()) {
                Log.i("Nome", "${it}")
                viewModel.setNome(it.toString())
            } else {
                viewModel.setNome("")
                Log.i("Nome", "Vazio")
            }
        }

        binding.textInputSobrenome.doAfterTextChanged { it ->
            if (!it.isNullOrEmpty() && !it.isBlank()) {
                Log.i("Sobrenome", "${it}")
                viewModel.setSobreNome(it.toString())
            } else {
                viewModel.setSobreNome("")
                Log.i("Sobrenome", "Vazio")
            }
        }

        binding.textInputEmail.doAfterTextChanged { it ->
            if (!it.isNullOrEmpty() && !it.isBlank()) {
                Log.i("Email", "${it}")
                viewModel.setEmail(it.toString())
            } else {
                viewModel.setEmail("")
                Log.i("Email", "Vazio")
            }
        }

        binding.textInputSenha.doAfterTextChanged { it ->
            if (!it.isNullOrEmpty() && !it.isBlank()) {
                Log.i("Senha", "${it}")
                viewModel.setSenha(it.toString())
            } else {
                viewModel.setSenha("")
                Log.i("Senha", "Vazio")
            }
        }


        binding.textInputConfirmaSenha.doAfterTextChanged { it ->
            if (!it.isNullOrEmpty() && !it.isBlank()) {
                Log.i("Confirmar Senha", "${it}")
                viewModel.setConfirmaSenha(it.toString())
            } else {
                viewModel.setConfirmaSenha("")
                Log.i("Confirmar Senha", "Vazio")
            }
        }
    }

    fun lerViewModel() {
        binding.textInputNome.setText(viewModel.nome.value.toString())
        binding.textInputSobrenome.setText(viewModel.sobreNome.value.toString())
        binding.textInputEmail.setText(viewModel.email.value.toString())
        binding.textInputSenha.setText(viewModel.senha.value.toString())
        binding.textInputConfirmaSenha.setText(viewModel.confirmaSenha.value.toString())
    }

}