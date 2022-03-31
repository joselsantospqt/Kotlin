package com.example.applicationalertaperigo.ui.home

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.applicationalertaperigo.R
import com.example.applicationalertaperigo.databinding.FragmentHomeDashboardBinding
import com.example.applicationalertaperigo.viewModel.HomeViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class HomeDashboardFragment : Fragment() {

    private var _binding: FragmentHomeDashboardBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by activityViewModels()
    private lateinit var auth: FirebaseAuth

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
        _binding = FragmentHomeDashboardBinding.inflate(inflater, container, false)
        val view = binding.root
        setup(view)
        return view
    }

    private fun setup(view: View) {
        lerViewModel()
        setupObservers(view)
        setupButton(view)
    }

    private fun lerViewModel() {
        viewModel.CarregaDadosUsuario(auth.currentUser?.uid.toString())
    }


    private fun setupButton(view: View) {
        binding.btnPerfil.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_homeDashboardFragment_to_homePerfilFragment)
        }
        binding.btnDenunciar.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_homeDashboardFragment_to_denunciaCadastroFragment)
        }
        binding.btnListDenuncia.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_homeDashboardFragment_to_denunciaListarFragment)
        }
    }

    private fun setupObservers(view: View) {
        viewModel.dadosPessoa.observe(viewLifecycleOwner, {
            if (it != null) {
                binding.txtNomeUsuario.setText(it.nome.toString() + " " + it.sobrenome.toString())
            }
        })

        viewModel.trocaFragment.observe(viewLifecycleOwner, {
            if (it != null) {
                when (it) {
                    2 -> {
                        viewModel.NavegaFragment(0)
                        Navigation.findNavController(view)
                            .navigate(R.id.action_homeDashboardFragment_to_homePerfilFragment)
                    }
                    3 -> {
                        viewModel.NavegaFragment(0)
                        Navigation.findNavController(view)
                            .navigate(R.id.action_homeDashboardFragment_to_denunciaCadastroFragment)
                    }
                    4 -> {
                        viewModel.NavegaFragment(0)
                        Navigation.findNavController(view)
                            .navigate(R.id.action_homeDashboardFragment_to_denunciaListarFragment)
                    }
                }

            }
        })

    }

}