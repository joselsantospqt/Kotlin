package com.example.applicationplanet.data

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class DataSource(resources: Resources) {
    private val initialPlanetList = planetList(resources)
    private val planetsLiveData = MutableLiveData(initialPlanetList)

    fun addPlanet(planet: Planet) {
        val currentList = planetsLiveData.value
        if (currentList == null) {
            planetsLiveData.postValue(listOf(planet))
        } else {
            val updatedList = currentList.toMutableList()
            updatedList.add(0, planet)
            planetsLiveData.postValue(updatedList)
        }
    }

    fun removePlanet(planet: Planet) {
        val currentList = planetsLiveData.value
        if (currentList != null) {
            val updatedList = currentList.toMutableList()
            updatedList.remove(planet)
            planetsLiveData.postValue(updatedList)
        }
    }


    /* Retorna um planeta dado um ID. */
    fun getPlanetForId(id: Long): Planet? {
        planetsLiveData.value?.let { flowers ->
            return flowers.firstOrNull{ it.id == id}
        }
        return null
    }

    fun getPlanetList(): LiveData<List<Planet>> {
        return planetsLiveData
    }

    /* Retorna uma imagem de planeta aleatório para planetas que são adicionados. */
    fun getRandomPlanetImageAsset(): Int? {
        val randomNumber = (initialPlanetList.indices).random()
        return initialPlanetList[randomNumber].image
    }

    companion object {
        private var INSTANCE: DataSource? = null

        fun getDataSource(resources: Resources): DataSource {
            return synchronized(DataSource::class) {
                val newInstance = INSTANCE ?: DataSource(resources)
                INSTANCE = newInstance
                newInstance
            }
        }
    }
}