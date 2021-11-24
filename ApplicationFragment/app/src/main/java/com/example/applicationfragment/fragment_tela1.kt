package com.example.applicationfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController


class fragment_tela1 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_tela1, container, false)

        val navController = findNavController()

        val btnTela2 = view.findViewById<Button>(R.id.btnTela2)
        btnTela2.setOnClickListener{
            navController.navigate(R.id.action_fragment_tela1_to_fragment_tela2)
        }

        val btnTela3 = view.findViewById<Button>(R.id.btnTela3)
        btnTela3.setOnClickListener{
            navController.navigate(R.id.action_fragment_tela1_to_fragment_tela3)

        }
        return view
    }


}