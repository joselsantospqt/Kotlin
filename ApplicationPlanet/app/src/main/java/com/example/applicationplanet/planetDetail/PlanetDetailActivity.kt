package com.example.applicationplanet.planetDetail

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.applicationplanet.R
import com.example.applicationplanet.planetList.PLANET_ID
import com.example.planets.planetDetail.PlanetDetailViewModel
import com.example.planets.planetDetail.PlanetDetailViewModelFactory

class PlanetDetailActivity : AppCompatActivity() {

    private val planetDetailViewModel by viewModels<PlanetDetailViewModel> {
        PlanetDetailViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planet_detail)

        var currentPlanetId: Long? = null

        /* Conecta as variáveis aos elementos da GUI. */
        val planetName: TextView = findViewById(R.id.planet_detail_name)
        val planetImage: ImageView = findViewById(R.id.planet_detail_image)
        val planetDescription: TextView = findViewById(R.id.planet_detail_description)
        val removePlanetButton: Button = findViewById(R.id.remove_button)

        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            currentPlanetId = bundle.getLong(PLANET_ID)
        }

        /* Se currentPlanetId não for nulo, obtenha o planeta correspondente e defina o nome,
        a imagem e a descrição */
        currentPlanetId?.let {
            val currentPlanet = planetDetailViewModel.getPlanetForId(it)
            planetName.text = currentPlanet?.name
            if (currentPlanet?.image == null) {
                planetImage.setImageResource(R.drawable.terra)
            } else {
                planetImage.setImageResource(currentPlanet.image)
            }
            planetDescription.text = currentPlanet?.description

            removePlanetButton.setOnClickListener {
                if (currentPlanet != null) {
                    planetDetailViewModel.removePlanet(currentPlanet)
                }
                finish()
            }
        }

    }
}