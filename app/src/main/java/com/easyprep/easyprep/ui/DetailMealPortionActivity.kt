package com.easyprep.easyprep.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import com.easyprep.easyprep.R
import com.easyprep.easyprep.data.model.Meal
import com.easyprep.easyprep.ui.adapters.MealDetailAdapter
import kotlinx.android.synthetic.main.activity_detail_meal_portion.*

class DetailMealPortionActivity : AppCompatActivity() {

    private lateinit var currentMeal: Meal
    private var metalDetailAdapter = MealDetailAdapter()
    private lateinit var mealTitles : Array<String>

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_meal_portion)
        mealTitles = resources.getStringArray(R.array.mealTitles)

        supportActionBar!!.run {
            setDefaultDisplayHomeAsUpEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }

        if (intent.getSerializableExtra(DayFragment.EXTRA_MEAL) != null) {
            this.currentMeal = intent.getSerializableExtra(DayFragment.EXTRA_MEAL) as Meal
            supportActionBar!!.title = mealTitles[this.currentMeal.nameId]
        }

        val layoutManager = LinearLayoutManager(this)
        recycleViewDetailId.layoutManager = layoutManager
        this.metalDetailAdapter.update(currentMeal)
        recycleViewDetailId.adapter = metalDetailAdapter
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val intent = Intent()
        if (currentMeal.valueIsChanged) {
            intent.putExtra(DayFragment.EXTRA_MEAL, this.currentMeal)
            setResult(Activity.RESULT_OK, intent)
        }
        finish()
        return super.onOptionsItemSelected(item)
    }
}