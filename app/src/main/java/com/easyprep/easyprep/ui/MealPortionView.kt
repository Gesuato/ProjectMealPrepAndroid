package com.easyprep.easyprep.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import com.easyprep.easyprep.R
import com.easyprep.easyprep.data.model.Meal

class MealPortionView(context: Context, attributeSet: AttributeSet) :
    LinearLayout(context, attributeSet) {

    private var btnNutrimentIds = arrayListOf(
        R.id.nutrimentVegetable,
        R.id.nutrimentFruit,
        R.id.nutrimentProtein,
        R.id.nutrimentCarbohydrate,
        R.id.nutrimentGoodFat,
        R.id.nutrimentSeed,
        R.id.nutrimentOil
    )
    private var btnNutrimentList = ArrayList<Button>()

    init {
        View.inflate(context, R.layout.meal_portion_view, this)
        for (currentId in btnNutrimentIds) {
            val button = findViewById<Button>(currentId)
            btnNutrimentList.add(button)
        }
    }

    fun setValues(meal: Meal) {
        for ((key, currentButton) in btnNutrimentList.withIndex()) {
            currentButton.visibility = View.GONE

            if (meal.nutriments[key].quantity > 0) {
                val quantity = meal.nutriments[key].quantity.toString()
                currentButton.text = quantity
                currentButton.visibility = View.VISIBLE
            }
        }
    }
}