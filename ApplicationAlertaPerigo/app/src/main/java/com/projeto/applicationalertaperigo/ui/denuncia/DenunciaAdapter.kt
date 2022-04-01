package com.projeto.applicationalertaperigo.ui.denuncia

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.projeto.applicationalertaperigo.databinding.DenunciaItemListBinding
import com.projeto.applicationalertaperigo.model.denuncia.DadosDenunciaComFoto

class DenunciaAdapter(private val onItemClicked: (DadosDenunciaComFoto) -> Unit) :
    ListAdapter<DadosDenunciaComFoto, DenunciaAdapter.MyViewHolder>(DiffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            DenunciaItemListBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }

        holder.bind(current)
    }


    class MyViewHolder(private val binding: DenunciaItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DadosDenunciaComFoto) {
            binding.txtdateRegistro.setText("Data & Hora: ${item.dateRegistro.toString()}")
            binding.txtDescricao.setText("Descrição: ${item.descricao.toString()}")
            binding.txtLatitude.setText("Latitude: ${item.latitude.toString()}")
            binding.txtLongitude.setText("Logitude: ${item.longitude.toString()}")
            binding.txtTitulo.setText("Titulo: ${item.titulo.toString()}")
            binding.imageViewFoto.setImageBitmap(item.foto)
        }
    }

    companion object {
        private val DiffCallBack = object : DiffUtil.ItemCallback<DadosDenunciaComFoto>() {
            override fun areItemsTheSame(
                oldItem: DadosDenunciaComFoto,
                newItem: DadosDenunciaComFoto
            ): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(
                oldItem: DadosDenunciaComFoto,
                newItem: DadosDenunciaComFoto
            ): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }
}