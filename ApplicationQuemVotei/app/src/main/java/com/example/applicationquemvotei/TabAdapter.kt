package com.example.applicationquemvotei

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class TabAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {

    val fragment = arrayOf(Etapa1(), Etapa2(), Etapa3())
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
       // Ao utilizar esse modo de carregar tela, verificar o peso que a fragments pode ocasionar no app
        // lateinit var fragment: Fragment
       // when(position){
       //     0 -> fragment = Etapa1()
       //     1 -> fragment = Etapa2()
       //     2 -> fragment = Etapa3()
       // }
        return fragment[position]
    }

}