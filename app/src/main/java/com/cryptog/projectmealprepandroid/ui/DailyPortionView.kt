package com.cryptog.projectmealprepandroid.ui

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import com.cryptog.projectmealprepandroid.R
import com.cryptog.projectmealprepandroid.data.model.DailyMealPlan

class DailyPortionView(context: Context, attributeSet: AttributeSet) :
    ConstraintLayout(context, attributeSet) {

    private var textViewIdDailyPortionList = arrayListOf(
        R.id.tVQuantityVegetables,
        R.id.tVQuantityFruits,
        R.id.tVQuantityProteins,
        R.id.tVQuantityCarbohydrates,
        R.id.tVQuantityGoodFat,
        R.id.tVQuantitySeeds,
        R.id.tVQuantityOils
    )
    private var dailyPortionsTextView = ArrayList<TextView>()

    init {
        View.inflate(context, R.layout.daily_portion_view, this)

        for (currentTextView in textViewIdDailyPortionList) {
            val textView = findViewById<TextView>(currentTextView)
            dailyPortionsTextView.add(textView)
        }
    }

    fun setValuesInPortionList(currentDailyMealPlan: DailyMealPlan) {
        for ((key, value) in currentDailyMealPlan.dailyPortions.withIndex()) {
            dailyPortionsTextView[key].text = value.quantity.toString()
        }
    }
}