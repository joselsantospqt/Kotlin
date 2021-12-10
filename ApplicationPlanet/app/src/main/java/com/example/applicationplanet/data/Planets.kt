package com.example.applicationplanet.data

import android.content.res.Resources
import com.example.applicationplanet.R

fun planetList(resources: Resources): List<Planet>{
    return  listOf(
        Planet(
            id = 1,
            name = resources.getString(R.string.planet1_name),
            image = R.drawable.mercurio,
            description = resources.getString(R.string.planet1_description)
        ),    Planet(
            id = 2,
            name = resources.getString(R.string.planet2_name),
            image = R.drawable.venus,
            description = resources.getString(R.string.planet2_description)
        ),    Planet(
            id = 3,
            name = resources.getString(R.string.planet3_name),
            image = R.drawable.terra,
            description = resources.getString(R.string.planet3_description)
        ),    Planet(
            id = 4,
            name = resources.getString(R.string.planet4_name),
            image = R.drawable.marte,
            description = resources.getString(R.string.planet4_description)
        ),    Planet(
            id = 5,
            name = resources.getString(R.string.planet5_name),
            image = R.drawable.jupiter,
            description = resources.getString(R.string.planet5_description)
        ),    Planet(
            id = 6,
            name = resources.getString(R.string.planet6_name),
            image = R.drawable.saturno,
            description = resources.getString(R.string.planet6_description)
        ),    Planet(
            id = 7,
            name = resources.getString(R.string.planet7_name),
            image = R.drawable.urano,
            description = resources.getString(R.string.planet7_description)
        ),    Planet(
            id = 8,
            name = resources.getString(R.string.planet8_name),
            image = R.drawable.netuno,
            description = resources.getString(R.string.planet8_description)
        )
    )
}