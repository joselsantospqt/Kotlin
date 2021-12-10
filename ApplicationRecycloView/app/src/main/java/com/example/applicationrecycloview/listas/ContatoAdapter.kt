package com.example.applicationrecycloview.listas

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.applicationrecycloview.dados.Contato

class ContatoAdapter: RecyclerView.Adapter<ContatoHolder>() {

    var dados = listOf<Contato>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContatoHolder {
        return ContatoHolder.gerar(parent)
    }

    override fun onBindViewHolder(holder: ContatoHolder, position: Int) {
        val contato = dados[position]
        holder.relacionar(contato)
    }

    override fun getItemCount() = dados.size

}