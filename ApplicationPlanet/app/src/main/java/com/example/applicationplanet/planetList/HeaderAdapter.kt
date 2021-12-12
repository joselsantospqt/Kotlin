package com.example.applicationplanet.planetList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.applicationplanet.R

/* Uma lista sempre exibindo um elemento: o número de planetas. */

class HeaderAdapter: RecyclerView.Adapter<HeaderAdapter.HeaderViewHolder>() {
    private var planetCount: Int = 0

    /* ViewHolder for displaying header. */
    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val planetNumberTextView: TextView = itemView.findViewById(R.id.planet_number_text)

        fun bind(planetCount: Int) {
            planetNumberTextView.text = planetCount.toString()
        }
    }

    /* Infla a view e retorna o HeaderViewHolder. */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.header_item, parent, false)
        return HeaderViewHolder(view)
    }

    /* Vincula o número de planetas ao cabeçalho. */
    override fun onBindViewHolder(holder: HeaderViewHolder, position: Int) {
        holder.bind(planetCount)
    }

    /* Retorna o número de itens, visto que há apenas um item no cabeçalho  */
    override fun getItemCount(): Int {
        return 1
    }

    /* Atualiza o cabeçalho para exibir o número de planetas quando um planeta é adicionado ou
     removido. */
    fun updatePlanetCount(updatedPlanetCount: Int) {
        planetCount = updatedPlanetCount
        notifyDataSetChanged()
    }
}
