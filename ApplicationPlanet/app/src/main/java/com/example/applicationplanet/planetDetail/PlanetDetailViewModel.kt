package com.example.planets.planetDetail

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.applicationplanet.data.DataSource
import com.example.applicationplanet.data.Planet

class PlanetDetailViewModel(private val datasource: DataSource) : ViewModel() {

    /* Consulta o data source para retornar um planeta que corresponda a um id. */
    fun getPlanetForId(id: Long) : Planet? {
        return datasource.getPlanetForId(id)
    }

    /* Queries datasource to remove a planet. */
    fun removePlanet(planet: Planet) {
        datasource.removePlanet(planet)
    }
}

class PlanetDetailViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlanetDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PlanetDetailViewModel(
                datasource = DataSource.getDataSource(context.resources)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}