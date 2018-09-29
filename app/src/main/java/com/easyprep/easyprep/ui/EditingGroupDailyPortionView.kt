package com.easyprep.easyprep.ui

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import com.easyprep.easyprep.R
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
            btnPlusOrLessTouched("PLUS")
        }

        btnLessDetail.setOnClickListener {
            btnPlusOrLessTouched("LESS")
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

    fun setOnCustomClickListener(onClickListener: EditingGroupDailyPortionOnClickListener) {
        this.editingGroupDailyPortionOnClickListener = onClickListener
    }

    fun setValueInButtonNutriment(value: Float) {
        this.buttonNutriment.text = value.toString()
    }

    fun setNutrimentTitle(title: String) {
        this.textViewTitle.text = title
    }

    private fun btnPlusOrLessTouched(type: String) {
        val valueOfChange = 0.5f
        var quantityString = "0.0"

        if (!this.buttonNutriment.text.isEmpty()) {
            quantityString = this.buttonNutriment.text.toString()
        }
        this.quantity = quantityString.toFloat()

        if (type == "PLUS") {
            this.quantity += valueOfChange
        } else if (type == "LESS") {
            if (this.quantity != 0.0f) {
                this.quantity -= valueOfChange
            }
        }

        this.buttonNutriment.text = quantity.toString()
        this.editingGroupDailyPortionOnClickListener.editingGroupDailyPortionOnClickListener(
            quantity
        )
    }
}