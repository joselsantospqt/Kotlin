package com.example.applicationestoque

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.applicationestoque.model.ProdutoComFoto

class ProdutoAdapter(private val dataSet: MutableList<ProdutoComFoto>) :
    RecyclerView.Adapter<ProdutoAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtNome: TextView
        val txtQuantidade: TextView
        val txtPreco: TextView
        val imageViewFoto: ImageView

        init {
            txtNome = view.findViewById(R.id.txtNome)
            txtQuantidade = view.findViewById(R.id.txtQuantidade)
            txtPreco = view.findViewById(R.id.txtPreco)
            imageViewFoto = view.findViewById(R.id.imageViewFoto)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.produto_item_list, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.txtNome.text = dataSet[position].nome
        viewHolder.txtQuantidade.text = dataSet[position].quantidade.toString()
        viewHolder.txtPreco.text = dataSet[position].preco.toString()
        viewHolder.imageViewFoto.setImageBitmap(dataSet[position].foto)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}
