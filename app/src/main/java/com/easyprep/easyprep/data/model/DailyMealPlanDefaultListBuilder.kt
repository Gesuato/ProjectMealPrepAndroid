package com.easyprep.easyprep.data.model

import com.easyprep.easyprep.R

class DailyMealPlanDefaultListBuilder : () -> List<DailyMealPlan> {
    override operator fun invoke(): List<DailyMealPlan> {

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

        val titlesDay = arrayOf(
            R.string.monday,
            R.string.tuesday,
            R.string.wednesday,
            R.string.thursday,
            R.string.friday,
            R.string.saturday,
            R.string.sunday
        )

        val dailyPortionsList = ArrayList<DailyMealPlan>()

        for (index in 0..6) {
            val dailyMealPlan: DailyMealPlan
            val quantityDefault = arrayOf(3.0f, 2.0f, 4.0f, 2.0f, 1.0f, 1.0f, 2.0f)
            val meals = ArrayList<Meal>()

            for (index in 0..5) {
                val nutriments = ArrayList<Nutriment>()
                for(index in 0..6){
                    val nutriment = Nutriment(nutrimentTitles[index], 0.0f, "")
                    nutriments.add(nutriment)
                }
                val meal = Meal(index, titlesOfMeals[index], nutriments, false)
                meals.add(meal)
            }

            val dailyPortions = ArrayList<Nutriment>()

            for (index in 0..6) {
                val nutriment = Nutriment(nutrimentTitles[index], quantityDefault[index], "")
                dailyPortions.add(nutriment)
            }

            dailyMealPlan = DailyMealPlan(index.toLong(), titlesDay[index], meals,dailyPortions)
            dailyPortionsList.add(dailyMealPlan)
        }
        return dailyPortionsList
    }
}