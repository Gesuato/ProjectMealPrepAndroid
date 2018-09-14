package com.cryptog.projectmealprepandroid.data.model

import com.cryptog.projectmealprepandroid.R

class Meal() {
    var nameId: Int = 0
    var nutriments: ArrayList<Nutriment>? = null

    init {
        val nutrimentTitles = arrayOf(
            R.string.veggies,
            R.string.fruits,
            R.string.proteins,
            R.string.carbohydrates,
            R.string.healthyFats,
            R.string.seedsAndDressings,
            R.string.oilsAndNutButters
        )

        for (index in 1..nutrimentTitles.size) {
            val nutriment = Nutriment()
            nutriment.nameId = nutrimentTitles[index]
            nutriment.items = ""
            nutriment.quantity = 0.0f
            nutriments!!.add(nutriment)
        }
    }
}