package com.cryptog.projectmealprepandroid.data.model

import com.cryptog.projectmealprepandroid.R
import java.io.Serializable

class Day : Serializable {

    var nameId: Int = 0
    var meals = ArrayList<Meal>()
    var dailyPortions = ArrayList<Nutriment>()


    init {
        val titlesOfMeals = arrayOf(
            R.string.breakfast,
            R.string.snack1,
            R.string.lunch,
            R.string.snack2,
            R.string.dinner,
            R.string.snack3
        )
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

        for (index in 0..5) {
            val meal = Meal()
            meal.nameId = titlesOfMeals[index]
            meals!!.add(meal)
        }

        for (index in 0..6) {
            val nutriment = Nutriment()
            nutriment.nameId = nutrimentTitles[index]
            nutriment.items = ""
            nutriment.quantity = quantityDefault[index]
            dailyPortions!!.add(nutriment)
        }
    }
}