package com.easyprep.easyprep.data.model

class DailyMealPlanDefaultListBuilder : () -> List<DailyMealPlan> {
    override operator fun invoke(): List<DailyMealPlan> {

        val dailyPortionsList = ArrayList<DailyMealPlan>()

        for (index in 0..6) {
            val dailyMealPlan: DailyMealPlan
            val quantityDefault = arrayOf(3.0f, 2.0f, 4.0f, 2.0f, 1.0f, 1.0f, 2.0f)
            val meals = ArrayList<Meal>()

            for (j in 0..5) {
                val nutriments = ArrayList<Nutriment>()
                for (k in 0..6) {
                    val nutriment = Nutriment(k, 0.0f, "")
                    nutriments.add(nutriment)
                }
                val meal = Meal(j, j, nutriments, false)
                meals.add(meal)
            }

            val dailyPortions = ArrayList<Nutriment>()

            for (key in 0..6) {
                val nutriment = Nutriment(key, quantityDefault[key], "")
                dailyPortions.add(nutriment)
            }

            dailyMealPlan = DailyMealPlan(index, meals, dailyPortions)
            dailyPortionsList.add(dailyMealPlan)
        }
        return dailyPortionsList
    }
}