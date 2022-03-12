package com.example.applicationestoque.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.applicationestoque.R
import com.example.applicationestoque.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        setupListerner(view)
        return view
    }

    fun setupListerner(view: View) {
        binding.btnCadastrar.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_cadastroFragment)
        }
    }

}