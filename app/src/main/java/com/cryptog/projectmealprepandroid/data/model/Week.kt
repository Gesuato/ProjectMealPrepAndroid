package com.cryptog.projectmealprepandroid.data.model

import com.cryptog.projectmealprepandroid.R
import java.io.Serializable

class Week : Serializable {
    var dailyMealPlanList = ArrayList<DailyMealPlan>()

    init {
        val titlesDay = arrayOf(
            R.string.monday,
            R.string.tuesday,
            R.string.wednesday,
            R.string.thursday,
            R.string.friday,
            R.string.saturday,
            R.string.sunday
        )

        for (index in 0..6) {
            val day = DailyMealPlan()
            day.day = titlesDay[index]
            this.dailyMealPlanList.add(day)
        }
    }
}