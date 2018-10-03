package com.easyprep.easyprep.data.model

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

@Entity
data class DailyMealPlan(
    @PrimaryKey
    val id: Long = 0,
    var day: Int = 0,
    @Embedded
    var meals: ArrayList<Meal>,
    @Embedded
    var dailyPortions: ArrayList<Nutriment>
) : Serializable, Cloneable {

    fun customClone(): DailyMealPlan {
        val cloneDailyMealPlan = super.clone() as DailyMealPlan
        cloneDailyMealPlan.meals = this.meals.clone() as ArrayList<Meal>
        cloneDailyMealPlan.dailyPortions = this.dailyPortions.clone() as ArrayList<Nutriment>
        return cloneDailyMealPlan
    }
}