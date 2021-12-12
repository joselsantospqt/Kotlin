package com.example.applicationplanet.planetList

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.applicationplanet.addPlanet.AddPlanetActivity
import com.example.applicationplanet.R
import com.example.applicationplanet.addPlanet.PLANET_DESCRIPTION
import com.example.applicationplanet.addPlanet.PLANET_NAME
import com.example.applicationplanet.data.Planet
import com.example.applicationplanet.planetDetail.PlanetDetailActivity


const val PLANET_ID = "planet id"

class PlanetsListActivity : AppCompatActivity() {
    private val newPlanetActivityRequestCode = 1
    private val planetsListViewModel by viewModels<PlanetsListViewModel> {
        PlanetsListViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /* Instancia headerAdapter e planetsAdapter. Ambos os adaptadores são adicionados ao
         concatAdapter que exibe o conteúdo sequencialmente */
        val headerAdapter = HeaderAdapter()
        val planetsAdapter = PlanetsAdapter { planet -> adapterOnClick(planet) }
        val concatAdapter = ConcatAdapter(headerAdapter, planetsAdapter)

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.adapter = concatAdapter

        planetsListViewModel.planetsLiveData.observe(this, {
            it?.let {
                planetsAdapter.submitList(it as MutableList<Planet>)
                headerAdapter.updatePlanetCount(it.size)
            }
        })

        val fab: View = findViewById(R.id.fab)
        fab.setOnClickListener {
            fabOnClick()
        }
    }

    /* Opens PlanetDetailActivity when RecyclerView item is clicked. */
    private fun adapterOnClick(planet: Planet) {
        val intent = Intent(this, PlanetDetailActivity()::class.java)
        intent.putExtra(PLANET_ID, planet.id)
        startActivity(intent)
    }

    /* Adiciona um planeta ao planetList quando FAB for clicado. */
    private fun fabOnClick() {
        val intent = Intent(this, AddPlanetActivity::class.java)
        startActivityForResult(intent, newPlanetActivityRequestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        /* Insere planeta no viewModel. */
        if (requestCode == newPlanetActivityRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.let { data ->
                val planetName = data.getStringExtra(PLANET_NAME)
                val planetDescription = data.getStringExtra(PLANET_DESCRIPTION)

                planetsListViewModel.insertPlanet(planetName, planetDescription)
            }
        }
    }
}