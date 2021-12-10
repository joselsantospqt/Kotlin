package com.example.applicationplanet.data

import android.content.res.Resources
import androidx.lifecycle.MutableLiveData

class DataSource(resources: Resources) {
    private val initialPlanetList = planetList(resources)
    private val planetsLiveData = MutableLiveData(initialPlanetList)

    fun addPlanet(planet: Planet){
        val currentList = planetsLiveData.value
        if(currentList == null)
            planetsLiveData.postValue(listOf(planet))
        else{
            val updatedList = currentList.toMutableList()
            updatedList.add(0, planet)
            planetsLiveData.postValue(updatedList)
        }
    }

    fun removePlanet(planet: Planet){
        val currentList = planetsLiveData.value
        if(currentList != null){
            val updatedList = currentList.toMutableList()
            updatedList.remove(planet)
            planetsLiveData.postValue(updatedList)
        }
    }

}