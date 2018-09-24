package com.cryptog.projectmealprepandroid.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.cryptog.projectmealprepandroid.R
import com.cryptog.projectmealprepandroid.data.model.Meal
import com.cryptog.projectmealprepandroid.ui.adapters.MealDetailAdapter
import kotlinx.android.synthetic.main.activity_detail_meal_portion.*

class DetailMealPortionActivity : AppCompatActivity() {

    private lateinit var currentMeal: Meal
    private var metalDetailAdapter = MealDetailAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_meal_portion)

        this.currentMeal = intent.getSerializableExtra(DayFragment.EXTRA_MEAL) as Meal
        Log.d("MealDetail", resources.getText(currentMeal.nameId).toString())

        val layoutManager = LinearLayoutManager(this)
        recycleViewDetailId.layoutManager = layoutManager
        this.metalDetailAdapter.update(currentMeal)
        recycleViewDetailId.adapter = metalDetailAdapter


        btnCancel.setOnClickListener {
            finish()
        }
        btnSave.setOnClickListener {
            val intent = Intent()
            intent.putExtra(DayFragment.EXTRA_MEAL, currentMeal)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}
