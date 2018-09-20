package com.cryptog.projectmealprepandroid.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.cryptog.projectmealprepandroid.R

class MealPortionView(context: Context, attributeSet: AttributeSet) :
    LinearLayout(context, attributeSet) {

    init {
        View.inflate(context, R.layout.meal_portion_view,this)
    }
}