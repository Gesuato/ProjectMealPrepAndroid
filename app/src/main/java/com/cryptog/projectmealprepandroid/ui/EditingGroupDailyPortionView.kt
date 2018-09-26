package com.cryptog.projectmealprepandroid.ui

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import com.cryptog.projectmealprepandroid.R
import kotlinx.android.synthetic.main.editing_group_daily_portion_view.view.*

class EditingGroupDailyPortionView(context: Context, attributeSet: AttributeSet) :
    ConstraintLayout(context, attributeSet) {

    private val buttonNutriment: Button
    private var quantity: Float = 0.0f
    private lateinit var editingGroupDailyPortionOnClickListener: EditingGroupDailyPortionOnClickListener

    init {
        View.inflate(context, R.layout.editing_group_daily_portion_view, this)

        this.buttonNutriment = findViewById(R.id.btnNutrimentDetail)
        setBackgroundInNutrimentButton(attributeSet)

        btnPlusDetail.setOnClickListener {
            var quantityString = "0.0"
            if (!buttonNutriment.text.isEmpty()) {
                quantityString = buttonNutriment.text.toString()
            }
            this.quantity = quantityString.toFloat()
            this.quantity += 0.5f
            this.buttonNutriment.text = quantity.toString()
            this.editingGroupDailyPortionOnClickListener.editingGroupDailyPortionOnClickListener(
                quantity
            )
        }

        btnLessDetail.setOnClickListener {
            var quantityString = "0.0"
            if (!this.buttonNutriment.text.isEmpty()) {
                quantityString = this.buttonNutriment.text.toString()
            }
            if (quantityString != "0.0") {
                this.quantity = quantityString.toFloat()
                this.quantity -= 0.5f
                this.buttonNutriment.text = quantity.toString()
                this.editingGroupDailyPortionOnClickListener.editingGroupDailyPortionOnClickListener(
                    this.quantity
                )
            }
        }
    }

    private fun setBackgroundInNutrimentButton(attributeSet: AttributeSet) {
        val editingGroupDailyPortionViewAttributes = context.obtainStyledAttributes(
            attributeSet,
            R.styleable.EditingGroupDailyPortionView,
            0,
            0
        )
        val background = editingGroupDailyPortionViewAttributes.getDrawable(
            R.styleable.EditingGroupDailyPortionView_nutrimentBackground
        )
        if (background != null) {
            buttonNutriment.background = background
        }
        editingGroupDailyPortionViewAttributes.recycle()
    }

    fun setOnCustomItemClickListener(onClickListener: EditingGroupDailyPortionOnClickListener) {
        this.editingGroupDailyPortionOnClickListener = onClickListener
    }

    fun setValueInButtonNutriment(value: Float) {
        this.buttonNutriment.text = value.toString()
    }

    fun setNutrimentTitle(title: String) {
        textViewTitle.text = title
    }
}