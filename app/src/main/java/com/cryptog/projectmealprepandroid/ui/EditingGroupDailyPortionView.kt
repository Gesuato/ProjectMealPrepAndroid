package com.cryptog.projectmealprepandroid.ui

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import com.cryptog.projectmealprepandroid.R


class EditingGroupDailyPortionView(context: Context, attributeSet: AttributeSet) :
    ConstraintLayout(context, attributeSet) {

    private val buttonNutriment: Button

    init {
        View.inflate(context, R.layout.editing_group_daily_portion_view, this)
        val editingGroupDailyPortionViewAttributes = context.obtainStyledAttributes(
            attributeSet,
            R.styleable.EditingGroupDailyPortionView,
            0,
            0
        )

        val background = editingGroupDailyPortionViewAttributes.getDrawable(
            R.styleable.EditingGroupDailyPortionView_nutrimentBackground
        )

        buttonNutriment = findViewById(R.id.btnNutrimentDetail)

        if (background != null) {
            buttonNutriment.background = background
        }

        editingGroupDailyPortionViewAttributes.recycle()
    }
}