package com.example.applicationestoque

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.applicationestoque.databinding.ProdutoItemListBinding
import com.example.applicationestoque.model.Produto
import com.example.applicationestoque.model.ProdutoComFoto

//https://developer.android.com/codelabs/android-room-with-a-view-kotlin?hl=pt-br#11

class ProdutoAdapter(private val onItemClicked: (Produto) -> Unit) : ListAdapter<Produto, ProdutoAdapter.MyViewHolder>(DiffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ProdutoItemListBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }

        holder.bind(current)
    }


    class MyViewHolder(private val binding: ProdutoItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Produto) {
            binding.txtNome.text = item.nome
            binding.txtDescricao.setText(item.descricao.toString())
            binding.txtQuantidade.setText(item.quantidade.toString())
            binding.txtPreco.setText("R$: ${item.preco.toString()}")
        }
    }

    companion object {
        private val DiffCallBack = object : DiffUtil.ItemCallback<Produto>() {
            override fun areItemsTheSame(oldItem: Produto, newItem: Produto): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Produto, newItem: Produto): Boolean {
                return oldItem.nome == newItem.nome
            }

        }
    }

}