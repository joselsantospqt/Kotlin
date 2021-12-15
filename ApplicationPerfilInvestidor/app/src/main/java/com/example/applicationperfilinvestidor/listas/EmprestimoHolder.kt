package com.example.applicationperfilinvestidor.listas

import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.applicationperfilinvestidor.R
import com.example.applicationperfilinvestidor.dados.Emprestimo

class EmprestimoHolder(view: View) : RecyclerView.ViewHolder(view),
    DialogInterface.OnClickListener {

    private var ID_PARAM: Long? = null
    val campoNome = view.findViewById<TextView>(R.id.txtNome)
    val campoValor = view.findViewById<TextView>(R.id.txtValor)
    val campoStatus = view.findViewById<TextView>(R.id.txtStatus)

    fun relacionar(emprestimo: Emprestimo) {
        campoNome.text = emprestimo.nome
        campoValor.text = "Valor: ${emprestimo.valor}"
        campoStatus.text = "Status: ${emprestimo.status}"


        itemView.setOnClickListener {
            val alertDialog = AlertDialog.Builder(itemView.context)
            ID_PARAM = emprestimo.idEmprestimo
            alertDialog.setTitle("Alerta")
            alertDialog.setMessage("Opção do item")
            alertDialog.setPositiveButton("Editar", this)
            alertDialog.setNegativeButton("Excluir", this)
            alertDialog.setCancelable(false)//alerta modal
            alertDialog.show()
        }
    }

    companion object {
        fun gerar(parent: ViewGroup): EmprestimoHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater
                .inflate(R.layout.linha_emprestimo, parent, false)
            return EmprestimoHolder(view)
        }

    }

    override fun onClick(dialog: DialogInterface?, id: Int) {
        val alertDialog = dialog as AlertDialog // Cast implicito
        val rotuloBotao = alertDialog.getButton(id).text
        when(rotuloBotao){
            "Editar" ->  Toast.makeText(itemView.context, "funcionalidade não foi implementada", Toast.LENGTH_SHORT).show()
            "Excluir" -> Toast.makeText(itemView.context, "funcionalidade não foi implementada", Toast.LENGTH_SHORT).show()
        }
    }
}