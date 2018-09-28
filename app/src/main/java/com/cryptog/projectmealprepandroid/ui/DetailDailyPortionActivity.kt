package com.cryptog.projectmealprepandroid.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.cryptog.projectmealprepandroid.R
import com.cryptog.projectmealprepandroid.data.model.Nutriment
import kotlinx.android.synthetic.main.activity_detail_daily_portion.*

class DetailDailyPortionActivity : AppCompatActivity() {

    private lateinit var currentDailyPotion: ArrayList<Nutriment>
    private lateinit var titleActionBar: String
    private var currentDayId: Int = 0

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_daily_portion)

        supportActionBar!!.run {
            setDefaultDisplayHomeAsUpEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }

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
        if (intent.getIntExtra("currentDay", 0) != 0) {
            currentDayId = intent.getIntExtra("currentDay", 0)
            this.titleActionBar = resources.getString(R.string.portionsOf) + " " + resources.getString(
                currentDayId
            )
            supportActionBar!!.title = titleActionBar
        }

        setValuesInNutrimentButtons(editingGroupDailyPotionList)
        setTitleInNutrimentButtons(editingGroupDailyPotionList)

        editingGroupVegetable.setOnCustomClickListener(object :
            EditingGroupDailyPortionOnClickListener {
            override fun editingGroupDailyPortionOnClickListener(quantity: Float) {
                currentDailyPotion[0].quantity = quantity
            }
        })
        editingGroupFruit.setOnCustomClickListener(object :
            EditingGroupDailyPortionOnClickListener {
            override fun editingGroupDailyPortionOnClickListener(quantity: Float) {
                currentDailyPotion[1].quantity = quantity
            }
        })
        editingGroupProtein.setOnCustomClickListener(object :
            EditingGroupDailyPortionOnClickListener {
            override fun editingGroupDailyPortionOnClickListener(quantity: Float) {
                currentDailyPotion[2].quantity = quantity
            }
        })
        editingGroupCarbohydrates.setOnCustomClickListener(object :
            EditingGroupDailyPortionOnClickListener {
            override fun editingGroupDailyPortionOnClickListener(quantity: Float) {
                currentDailyPotion[3].quantity = quantity
            }
        })
        editingGroupGoodFat.setOnCustomClickListener(object :
            EditingGroupDailyPortionOnClickListener {
            override fun editingGroupDailyPortionOnClickListener(quantity: Float) {
                currentDailyPotion[4].quantity = quantity
            }
        })
        editingGroupSeed.setOnCustomClickListener(object :
            EditingGroupDailyPortionOnClickListener {
            override fun editingGroupDailyPortionOnClickListener(quantity: Float) {
                currentDailyPotion[5].quantity = quantity
            }
        })
        editingGroupOil.setOnCustomClickListener(object :
            EditingGroupDailyPortionOnClickListener {
            override fun editingGroupDailyPortionOnClickListener(quantity: Float) {
                currentDailyPotion[6].quantity = quantity
            }
        })
    }

    private fun setValuesInNutrimentButtons(editingGroupDailyPotionList: ArrayList<EditingGroupDailyPortionView>) {
        for ((key, group) in editingGroupDailyPotionList.withIndex()) {
            val quantity = this.currentDailyPotion[key].quantity
            group.setValueInButtonNutriment(quantity)
        }
    }

    private fun setTitleInNutrimentButtons(editingGroupDailyPotionList: ArrayList<EditingGroupDailyPortionView>) {
        for ((key, group) in editingGroupDailyPotionList.withIndex()) {
            val title = resources.getText(this.currentDailyPotion[key].nameId) as String
            group.setNutrimentTitle(title)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val intent = Intent()
        intent.putExtra(DayFragment.EXTRA_DAILYPORTION, this.currentDailyPotion)
        setResult(Activity.RESULT_OK, intent)
        finish()
        return super.onOptionsItemSelected(item)
    }
}