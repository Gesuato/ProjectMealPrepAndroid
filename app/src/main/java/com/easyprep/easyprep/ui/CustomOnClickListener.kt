package com.easyprep.easyprep.ui
import com.easyprep.easyprep.data.model.Meal

interface CustomOnClickListener {
    fun onCustomItemClickListener(meal: Meal)
}