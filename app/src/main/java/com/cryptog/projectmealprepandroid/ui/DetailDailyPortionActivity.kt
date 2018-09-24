package com.cryptog.projectmealprepandroid.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import com.cryptog.projectmealprepandroid.R

class DetailDailyPortionActivity : AppCompatActivity() {

    private val listEditingGroupDailyPortionView = ArrayList<EditingGroupDailyPortionView>()
    private val listIdEditingGroupDailyPortion = arrayListOf(
        R.id.nutrimentVegetable,
        R.id.nutrimentFruit,
        R.id.nutrimentProtein,
        R.id.nutrimentCarbohydrate,
        R.id.nutrimentGoodFat,
        R.id.nutrimentSeed,
        R.id.nutrimentOil
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_daily_portion)
    }
}
