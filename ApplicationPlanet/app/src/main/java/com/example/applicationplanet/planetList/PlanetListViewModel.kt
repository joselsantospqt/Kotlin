package com.example.applicationplanet.planetList

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.applicationplanet.data.DataSource
import com.example.applicationplanet.data.Planet
import kotlin.random.Random

class PlanetsListViewModel(val dataSource: DataSource) : ViewModel() {

    val planetsLiveData = dataSource.getPlanetList()

    /* If the name and description are present, create new Planet and add it to the datasource */
    fun insertPlanet(planetName: String?, planetDescription: String?) {
        if (planetName == null || planetDescription == null) {
            return
        }

        val image = dataSource.getRandomPlanetImageAsset()
        val newPlanet = Planet(
            Random.nextLong(),
            planetName,
            image,
            planetDescription
        )

        dataSource.addPlanet(newPlanet)
    }
}

class PlanetsListViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlanetsListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PlanetsListViewModel(
                dataSource = DataSource.getDataSource(context.resources)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
