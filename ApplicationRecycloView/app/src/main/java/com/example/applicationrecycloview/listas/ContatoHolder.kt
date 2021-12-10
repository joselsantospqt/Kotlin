package com.example.applicationrecycloview.listas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.applicationrecycloview.R
import com.example.applicationrecycloview.dados.Contato

class ContatoHolder(view: View): RecyclerView.ViewHolder(view) {

    val campoNome = view.findViewById<TextView>(R.id.txtNome)
    val campoTelefone = view.findViewById<TextView>(R.id.txtTelefone)
    val campoEmail = view.findViewById<TextView>(R.id.txtEmail)

    fun relacionar(contato: Contato){
        campoNome.text = contato.nome
        campoTelefone.text = "Tel: ${contato.telefone}"
        campoEmail.text = "E-mail: ${contato.email}"

    }

    companion object{
        fun gerar(parent: ViewGroup): ContatoHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater
                .inflate(R.layout.linha_contato, parent, false)
            return ContatoHolder(view)
        }
    }
}