package com.cryptog.projectmealprepandroid.data.model

import com.cryptog.projectmealprepandroid.R
import java.io.Serializable

class Meal : Serializable {
    var id: Int = 0
    var nameId: Int = 0
    var nutriments = ArrayList<Nutriment>()

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

        for (index in 0..6) {
            val nutriment = Nutriment()
            nutriment.nameId = nutrimentTitles[index]
            nutriment.items = ""
            nutriment.quantity = 0.0f
            nutriments.add(nutriment)
        }
    }
}