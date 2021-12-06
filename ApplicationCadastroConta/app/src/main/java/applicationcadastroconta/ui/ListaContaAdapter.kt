package applicationcadastroconta.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import applicationcadastroconta.domain.Conta

class ListaContaAdapter (): RecyclerView.Adapter<ListaContaAdapter.ViewHolder>(){
    var listaContas = listOf<Conta>()
    set(value){
        field = value
        notifyDataSetChanged()

    }

    lateinit var itemListerner : RecycleViewItemListener

    fun setRecycleViewItemListener(listener : RecycleViewItemListener){
        itemListerner = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListaContaAdapter.ViewHolder {
      val view = LayoutInflater
          .from(parent.context)
          .inflate(R.layout.conta_listrow, parent, false)
        return ListaContaAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listaContas[position], itemListerner, position)
    }

    override fun getItemCount(): Int {
       return listaContas.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bindItem(conta: Conta, itemListener : RecycleViewItemListener, position : Int){
            val rowNome = itemView.findViewById<TextView>(R.id.rowNome)
            rowNome.setText(conta.nome)
            val rowEmail = itemView.findViewById<TextView>(R.id.rowEmail)
            rowNome.setText(conta.email)
            val rowFone = itemView.findViewById<TextView>(R.id.rowFone)
            rowNome.setText(conta.fone)

            itemView.setOnClickListener{
                itemListener.recyclerViewItemClicked(it, conta.id)
            }
        }
    }


}