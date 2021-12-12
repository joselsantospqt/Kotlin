package com.example.applicationplanet.planetList


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.applicationplanet.R
import com.example.applicationplanet.data.Planet

class PlanetsAdapter(private val onClick: (Planet) -> Unit) :
    ListAdapter<Planet, PlanetsAdapter.PlanetViewHolder>(PlanetDiffCallback) {

    /* ViewHolder for Planet, takes in the inflated view and the onClick behavior. */
    class PlanetViewHolder(itemView: View, val onClick: (Planet) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val planetTextView: TextView = itemView.findViewById(R.id.planet_text)
        private val planetImageView: ImageView = itemView.findViewById(R.id.planet_image)
        private var currentPlanet: Planet? = null

        init {
            itemView.setOnClickListener {
                currentPlanet?.let {
                    onClick(it)
                }
            }
        }

        /* Bind planet name and image. */
        fun bind(planet: Planet) {
            currentPlanet = planet

            planetTextView.text = planet.name
            if (planet.image != null) {
                planetImageView.setImageResource(planet.image)
            } else {
                planetImageView.setImageResource(R.drawable.terra)
            }
        }
    }

    /* Creates and inflates view and return PlanetViewHolder. */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanetViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.planet_item, parent, false)
        return PlanetViewHolder(view, onClick)
    }

    /* Gets current planet and uses it to bind view. */
    override fun onBindViewHolder(holder: PlanetViewHolder, position: Int) {
        val flower = getItem(position)
        holder.bind(flower)

    }
}

object PlanetDiffCallback : DiffUtil.ItemCallback<Planet>() {
    override fun areItemsTheSame(oldItem: Planet, newItem: Planet): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Planet, newItem: Planet): Boolean {
        return oldItem.id == newItem.id
    }
}
