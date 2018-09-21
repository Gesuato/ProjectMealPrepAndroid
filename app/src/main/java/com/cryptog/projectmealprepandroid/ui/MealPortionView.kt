package com.cryptog.projectmealprepandroid.ui

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import com.cryptog.projectmealprepandroid.R
import com.cryptog.projectmealprepandroid.data.model.Meal

class MealPortionView(context: Context, attributeSet: AttributeSet) :
    LinearLayout(context, attributeSet) {

    private var btnVege: Button

    init {
        View.inflate(context, R.layout.meal_portion_view, this)
        btnVege = findViewById(R.id.nutrimentVegetable)
    }

    fun setValue(meal: Meal) {
        btnVege.visibility = View.GONE
        if (meal.nutriments[0].quantity > 0) {
            btnVege.visibility = View.VISIBLE
            btnVege.text = meal.nutriments[0].quantity.toString()
            Log.d("Teste","teste")
        }
    }
}