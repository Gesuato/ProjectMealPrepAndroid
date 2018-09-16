package com.cryptog.projectmealprepandroid.data.model

import com.cryptog.projectmealprepandroid.R

class Day() {

    var nameId: Int = 0
    var meals: ArrayList<Meal>? = null

    init {
        val titlesOfMeals = arrayOf(
            R.string.breakfast,
            R.string.snack1,
            R.string.lunch,
            R.string.snack2,
            R.string.dinner,
            R.string.snack3
        )

        for (index in 1..titlesOfMeals.size) {
            val meal = Meal()
            meal.nameId = titlesOfMeals[index]
            meals!!.add(meal)
        }
    }
}