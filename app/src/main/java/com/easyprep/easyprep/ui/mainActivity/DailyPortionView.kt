package com.easyprep.easyprep.ui.mainActivity

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.easyprep.easyprep.R

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
    private var btnCopy: ImageButton
    private var imgBtnDetailEdit: ImageButton
    private var btnsDailyPortion = ArrayList<TextView>()

    init {
        View.inflate(context, R.layout.daily_portion_view, this)
        for (currentId in btnIdDailyPortionList) {
            val currentButton = findViewById<Button>(currentId)
            btnsDailyPortion.add(currentButton)
        }
        this.imgBtnDetailEdit = findViewById(R.id.imgBtnDetailEdit)
        this.btnCopy = findViewById(R.id.imgBtnCopy)
    }

    fun setValuesInPortionList(currentDailyPortions: ArrayList<Float>) {
        for ((key, value) in currentDailyPortions.withIndex()) {
            btnsDailyPortion[key].text = value.toString()
        }
    }
}