package com.example.applicationloginfirebase.login

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
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.applicationloginfirebase.R
import com.example.applicationloginfirebase.databinding.FragmentCadastarEnderecoBinding
import com.example.applicationloginfirebase.databinding.FragmentCadastrarPessoaBinding
import com.example.applicationloginfirebase.viewModel.MainViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class CadastarEnderecoFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private var _binding: FragmentCadastarEnderecoBinding? = null
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
        _binding = FragmentCadastarEnderecoBinding.inflate(inflater, container, false)
        val view = binding.root
        lerViewModel()
        setup(view)
        return view
    }

    fun setup(view: View) {
        lerViewModel()
        setuptextChange()
        setupButton(view)
        setupObservers()
    }

    fun setupButton(view: View) {
        binding.btnAvancar.setOnClickListener {
            if (viewModel.verificacaoDadosEndereco())
                cadastroFirebase()
            else {
                Toast.makeText(activity, "Preencha os campos", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnVoltar.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_cadastarEnderecoFragment_to_cadastrarPessoaFragment)
        }
    }

    fun cadastroFirebase() {
        viewModel.cadastroFireBase()
    }

    fun setuptextChange() {
        binding.textInputCEP.doAfterTextChanged { it ->
            if (!it.isNullOrEmpty() && !it.isBlank() && it?.length == 8) {
                Log.i("CEP", "${it}")
                viewModel.setCEP(it.toString())
            } else {
                viewModel.setCEP("")
                Log.i("CEP", "Vazio")
            }
        }

        binding.textInputEstado.doAfterTextChanged { it ->
            if (!it.isNullOrEmpty() && !it.isBlank()) {
                Log.i("Estado", "${it}")
                viewModel.setEstado(it.toString())
            } else {
                viewModel.setEstado("")
                Log.i("Estado", "Vazio")
            }
        }

        binding.textInputCidade.doAfterTextChanged { it ->
            if (!it.isNullOrEmpty() && !it.isBlank()) {
                Log.i("Cidade", "${it}")
                viewModel.setCidade(it.toString())
            } else {
                viewModel.setCidade("")
                Log.i("Cidade", "Vazio")
            }
        }

        binding.textInputBairro.doAfterTextChanged { it ->
            if (!it.isNullOrEmpty() && !it.isBlank()) {
                Log.i("Bairro", "${it}")
                viewModel.setBairro(it.toString())
            } else {
                viewModel.setBairro("")
                Log.i("Bairro", "Vazio")
            }
        }

        binding.textInputLogradouro.doAfterTextChanged { it ->
            if (!it.isNullOrEmpty() && !it.isBlank()) {
                Log.i("Logradouro", "${it}")
                viewModel.setLogradouro(it.toString())
            } else {
                viewModel.setLogradouro("")
                Log.i("Logradouro", "Vazio")
            }
        }


        binding.textInputNumero.doAfterTextChanged { it ->
            if (!it.isNullOrEmpty() && !it.isBlank()) {
                Log.i("Numero", "${it}")
                viewModel.setNumero(it.toString())
            } else {
                viewModel.setNumero("")
                Log.i("Numero", "Vazio")
            }
        }

        binding.textInputComplemento.doAfterTextChanged { it ->
            if (!it.isNullOrEmpty() && !it.isBlank()) {
                Log.i("Complemento", "${it}")
                viewModel.setComplemento(it.toString())
            } else {
                viewModel.setComplemento("")
                Log.i("Complemento", "Vazio")
            }
        }
    }

    fun setupObservers() {
        viewModel.cep.observe(viewLifecycleOwner) {
            if (it.length == 8) {
                viewModel.getEndereco()
            }
        }
        viewModel.atualizaEndereco.observe(viewLifecycleOwner) {
            lerViewModelSemCep()
        }
    }

    fun lerViewModelSemCep() {
        binding.textInputBairro.setText(viewModel.bairro.value.toString())
        binding.textInputEstado.setText(viewModel.estado.value.toString())
        binding.textInputComplemento.setText(viewModel.complemento.value.toString())
        binding.textInputNumero.setText(viewModel.numero.value.toString())
        binding.textInputLogradouro.setText(viewModel.logradouro.value.toString())
        binding.textInputCidade.setText(viewModel.cidade.value.toString())

    }

    fun lerViewModel() {
        binding.textInputCEP.setText(viewModel.cep.value.toString())
        binding.textInputBairro.setText(viewModel.bairro.value.toString())
        binding.textInputEstado.setText(viewModel.estado.value.toString())
        binding.textInputComplemento.setText(viewModel.complemento.value.toString())
        binding.textInputNumero.setText(viewModel.numero.value.toString())
        binding.textInputLogradouro.setText(viewModel.logradouro.value.toString())
        binding.textInputCidade.setText(viewModel.cidade.value.toString())
    }


}