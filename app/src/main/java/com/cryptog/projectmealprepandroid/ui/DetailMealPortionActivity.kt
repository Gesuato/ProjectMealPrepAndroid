package com.cryptog.projectmealprepandroid.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import com.cryptog.projectmealprepandroid.R
import com.cryptog.projectmealprepandroid.data.model.Meal
import com.cryptog.projectmealprepandroid.ui.adapters.MealDetailAdapter
import kotlinx.android.synthetic.main.activity_detail_meal_portion.*

class DetailMealPortionActivity : AppCompatActivity() {

    private lateinit var currentMeal: Meal
    private var metalDetailAdapter = MealDetailAdapter()

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_meal_portion)

        supportActionBar!!.run {
            setDefaultDisplayHomeAsUpEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }

        if (intent.getSerializableExtra(DayFragment.EXTRA_MEAL) != null) {
            this.currentMeal = intent.getSerializableExtra(DayFragment.EXTRA_MEAL) as Meal
            supportActionBar!!.setTitle(this.currentMeal.nameId)
        }

        val layoutManager = LinearLayoutManager(this)
        recycleViewDetailId.layoutManager = layoutManager
        this.metalDetailAdapter.update(currentMeal)
        recycleViewDetailId.adapter = metalDetailAdapter
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val intent = Intent()
        intent.putExtra(DayFragment.EXTRA_MEAL, this.currentMeal)
        setResult(Activity.RESULT_OK, intent)
        finish()
        return super.onOptionsItemSelected(item)
    }
}
