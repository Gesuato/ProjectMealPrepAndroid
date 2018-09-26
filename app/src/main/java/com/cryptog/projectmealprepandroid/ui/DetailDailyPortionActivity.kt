package com.cryptog.projectmealprepandroid.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.cryptog.projectmealprepandroid.R
import com.cryptog.projectmealprepandroid.data.model.Nutriment
import kotlinx.android.synthetic.main.activity_detail_daily_portion.*

class DetailDailyPortionActivity : AppCompatActivity() {

    private lateinit var currentDailyPotion: ArrayList<Nutriment>

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_daily_portion)
        supportActionBar!!.setDefaultDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        val editingGroupDailyPotionList = arrayListOf(
            editingGroupVegetable,
            editingGroupFruit,
            editingGroupProtein,
            editingGroupCarbohydrates,
            editingGroupGoodFat,
            editingGroupSeed,
            editingGroupOil
        )

        if (intent.getSerializableExtra(DayFragment.EXTRA_DAILYPORTION) != null) {
            this.currentDailyPotion =
                    intent.getSerializableExtra(DayFragment.EXTRA_DAILYPORTION) as ArrayList<Nutriment>
        }

        setValuesInNutrimentButtons(editingGroupDailyPotionList)

        editingGroupVegetable.setOnCustomItemClickListener(object :
            EditingGroupDailyPortionOnClickListener {
            override fun editingGroupDailyPortionOnClickListener(quantity: Float) {

            }
        })
        editingGroupFruit.setOnCustomItemClickListener(object :
            EditingGroupDailyPortionOnClickListener {
            override fun editingGroupDailyPortionOnClickListener(quantity: Float) {

            }
        })
        editingGroupProtein.setOnCustomItemClickListener(object :
            EditingGroupDailyPortionOnClickListener {
            override fun editingGroupDailyPortionOnClickListener(quantity: Float) {

            }
        })
        editingGroupCarbohydrates.setOnCustomItemClickListener(object :
            EditingGroupDailyPortionOnClickListener {
            override fun editingGroupDailyPortionOnClickListener(quantity: Float) {

            }
        })
        editingGroupGoodFat.setOnCustomItemClickListener(object :
            EditingGroupDailyPortionOnClickListener {
            override fun editingGroupDailyPortionOnClickListener(quantity: Float) {

            }
        })
        editingGroupSeed.setOnCustomItemClickListener(object :
            EditingGroupDailyPortionOnClickListener {
            override fun editingGroupDailyPortionOnClickListener(quantity: Float) {

            }
        })
        editingGroupOil.setOnCustomItemClickListener(object :
            EditingGroupDailyPortionOnClickListener {
            override fun editingGroupDailyPortionOnClickListener(quantity: Float) {

            }
        })
    }

    private fun setValuesInNutrimentButtons(editingGroupDailyPotionList: ArrayList<EditingGroupDailyPortionView>) {
        for ((key, group) in editingGroupDailyPotionList.withIndex()) {
            val quantity = this.currentDailyPotion[key].quantity
            group.setValueInButtonNutriment(quantity)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        finish()

        return super.onOptionsItemSelected(item)
    }
}