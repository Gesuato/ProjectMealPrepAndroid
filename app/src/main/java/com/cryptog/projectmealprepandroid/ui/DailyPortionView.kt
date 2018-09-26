package com.cryptog.projectmealprepandroid.ui

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.cryptog.projectmealprepandroid.R
import com.cryptog.projectmealprepandroid.data.model.Nutriment

class DailyPortionView(context: Context, attributeSet: AttributeSet) :
    ConstraintLayout(context, attributeSet) {

    private var btnIdDailyPortionList = arrayListOf(
        R.id.btnVegetables,
        R.id.btnFruits,
        R.id.btnProteins,
        R.id.btnCarbohydrates,
        R.id.btnGoodFat,
        R.id.btnSeeds,
        R.id.btnOils
    )
    private var imgBtnDetailEdit : ImageButton
    private var btnsDailyPortion = ArrayList<TextView>()

    init {
        View.inflate(context, R.layout.daily_portion_view, this)

        for (currentId in btnIdDailyPortionList) {
            val currentButton = findViewById<Button>(currentId)
            btnsDailyPortion.add(currentButton)
        }
        imgBtnDetailEdit = findViewById(R.id.imgBtnDetailEdit)
    }

    fun setValuesInPortionList(currentDailyPortions: ArrayList<Nutriment>) {
        for ((key, value) in currentDailyPortions.withIndex()) {
            btnsDailyPortion[key].text = value.quantity.toString()
        }
    }
}