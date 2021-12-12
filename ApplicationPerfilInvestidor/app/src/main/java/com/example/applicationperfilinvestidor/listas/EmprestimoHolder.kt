package com.example.applicationperfilinvestidor.listas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.applicationperfilinvestidor.R
import com.example.applicationperfilinvestidor.dados.Emprestimo

class EmprestimoHolder(view: View): RecyclerView.ViewHolder(view) {

    val campoNome = view.findViewById<TextView>(R.id.txtNome)
    val campoTelefone = view.findViewById<TextView>(R.id.txtTelefone)
    val campoEmail = view.findViewById<TextView>(R.id.txtEmail)

    fun relacionar(emprestimo: Emprestimo){
        campoNome.text = emprestimo.nome
        campoTelefone.text = "Tel: ${emprestimo.telefone}"
        campoEmail.text = "E-mail: ${emprestimo.email}"

    }

    companion object{
        fun gerar(parent: ViewGroup): EmprestimoHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater
                .inflate(R.layout.linha_emprestimo, parent, false)
            return EmprestimoHolder(view)
        }
    }
}