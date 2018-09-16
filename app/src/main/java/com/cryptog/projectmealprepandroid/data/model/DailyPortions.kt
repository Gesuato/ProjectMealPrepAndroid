package com.cryptog.projectmealprepandroid.data.model

import com.cryptog.projectmealprepandroid.R

class DailyPortions {
    private var dailyPortions = ArrayList<Nutriment>()

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
        val quantityDefault = arrayOf(3.0f, 2.0f, 4.0f, 2.0f, 1.0f, 1.0f, 2.0f)
        for (index in 0..6) {
            val nutriment = Nutriment()
            nutriment.nameId = nutrimentTitles[index]
            nutriment.items = ""
            nutriment.quantity = quantityDefault[index]
            dailyPortions!!.add(nutriment)
        }
    }
}