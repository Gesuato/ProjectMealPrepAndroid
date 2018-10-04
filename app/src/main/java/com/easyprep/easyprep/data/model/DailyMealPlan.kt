package com.easyprep.easyprep.data.model

import java.io.Serializable

data class DailyMealPlan(
    var day: Int = 0,
    var meals: List<Meal>,
    var dailyPortions: List<Nutriment>
) : Serializable, Cloneable {

    fun customClone(): DailyMealPlan {
        val cloneDailyMealPlan = super.clone() as DailyMealPlan
        cloneDailyMealPlan.meals = ArrayList(meals)
        cloneDailyMealPlan.dailyPortions = ArrayList(dailyPortions)
        return cloneDailyMealPlan
    }
}