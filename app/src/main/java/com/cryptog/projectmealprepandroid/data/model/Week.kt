package com.cryptog.projectmealprepandroid.data.model

import com.cryptog.projectmealprepandroid.R

class Week() {
    var days: ArrayList<Day>? = null

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

        for (index in 1..titlesDay.size) {
            val day = Day()
            day.nameId = titlesDay[index]
            days!!.add(day)
        }
    }
}