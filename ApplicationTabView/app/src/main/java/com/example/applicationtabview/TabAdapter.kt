package com.example.applicationtabview

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class TabAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {

    val fragment = arrayOf(Fornecedores(), Contatos(), Produtos())
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
       // Ao utilizar esse modo de carregar tela, verificar o peso que a fragments pode ocasionar no app
        // lateinit var fragment: Fragment
       // when(position){
       //     0 -> fragment = Fornecedores ()
       //     1 -> fragment = Contatos()
       //     2 -> fragment = Produtos()
       // }
        return fragment[position]
    }

}