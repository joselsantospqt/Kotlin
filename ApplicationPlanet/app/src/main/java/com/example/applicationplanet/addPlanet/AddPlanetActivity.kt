package com.example.applicationplanet.addPlanet

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.applicationplanet.R
import com.google.android.material.textfield.TextInputEditText

const val PLANET_NAME = "name"
const val PLANET_DESCRIPTION = "description"

class AddPlanetActivity : AppCompatActivity() {
    private lateinit var addPlanetName: TextInputEditText
    private lateinit var addPlanetDescription: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_planet_layout)

        findViewById<Button>(R.id.done_button).setOnClickListener {
            addPlanet()
        }
        addPlanetName = findViewById(R.id.add_planet_name)
        addPlanetDescription = findViewById(R.id.add_planet_description)
    }


    /* A ação onClick para o botão done. Fecha a activity e retorna o nome e a descrição do
    novo planeta como parte da intent. Se o nome ou a descrição estiverem faltando, o resultado
     será definido como cancelado. */
    private fun addPlanet() {
        val resultIntent = Intent()

        if (addPlanetName.text.isNullOrEmpty() || addPlanetDescription.text.isNullOrEmpty()) {
            setResult(Activity.RESULT_CANCELED, resultIntent)
        } else {
            val name = addPlanetName.text.toString()
            val description = addPlanetDescription.text.toString()
            resultIntent.putExtra(PLANET_NAME, name)
            resultIntent.putExtra(PLANET_DESCRIPTION, description)
            setResult(Activity.RESULT_OK, resultIntent)
        }
        finish()
    }
}