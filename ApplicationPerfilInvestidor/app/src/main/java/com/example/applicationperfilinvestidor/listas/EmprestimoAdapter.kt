package com.example.applicationperfilinvestidor.listas

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.applicationperfilinvestidor.dados.Emprestimo

class EmprestimoAdapter: RecyclerView.Adapter<EmprestimoHolder>() {

    var dados = listOf<Emprestimo>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmprestimoHolder {
        return EmprestimoHolder.gerar(parent)
    }

    override fun onBindViewHolder(holder: EmprestimoHolder, position: Int) {
        val emprestimo = dados[position]
        holder.relacionar(emprestimo)
    }

    override fun getItemCount() = dados.size

}