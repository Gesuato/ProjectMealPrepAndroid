package com.easyprep.easyprep.data.model

import java.io.Serializable

data class DailyMealPlan(
    var dayId: Int = 0,
    var meals: List<Meal> = emptyList(),
    var dailyPortions: List<Nutriment> = emptyList()
) : Serializable, Cloneable {

    fun customClone(): DailyMealPlan {
        val cloneDailyMealPlan = super.clone() as DailyMealPlan

        cloneDailyMealPlan.meals = ArrayList(meals)
        cloneDailyMealPlan.dailyPortions = ArrayList(dailyPortions)
        return cloneDailyMealPlan
    }
}