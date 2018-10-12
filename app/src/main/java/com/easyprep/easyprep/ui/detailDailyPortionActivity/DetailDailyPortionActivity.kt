package com.easyprep.easyprep.ui.detailDailyPortionActivity

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.easyprep.easyprep.R
import com.easyprep.easyprep.data.model.Nutriment
import com.easyprep.easyprep.ui.interfaces.EditingGroupDailyPortionOnClickListener
import com.easyprep.easyprep.ui.mainActivity.DayFragment
import kotlinx.android.synthetic.main.activity_detail_daily_portion.*

class DetailDailyPortionActivity : AppCompatActivity() {

    private lateinit var currentDailyPotion: ArrayList<Nutriment>
    private lateinit var titleActionBar: String
    private var currentDayId: Int = 0
    private var valuesIsChanged = false
    private lateinit var nutrimentTitles : Array<String>
    private lateinit var dayTitles : Array<String>

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_daily_portion)

        if(!::nutrimentTitles.isInitialized){
            this.nutrimentTitles = resources.getStringArray(R.array.nutrimentTitles)
        }

        if(!::dayTitles.isInitialized){
            this.dayTitles = resources.getStringArray(R.array.dayTitles)
        }

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

        intent.getIntExtra("CURRENTDAY",0).run {
            currentDayId = intent.getIntExtra("CURRENTDAY", 0)
            titleActionBar = resources.getString(R.string.portionsOf) + " " +
                    dayTitles[currentDayId]
            supportActionBar!!.title = titleActionBar
        }


        setValuesInNutrimentButtons(editingGroupDailyPotionList)
        setTitleInNutrimentButtons(editingGroupDailyPotionList)

        (0..6).forEach{ index ->
            editingGroupDailyPotionList[index].setOnCustomClickListener(object :
                EditingGroupDailyPortionOnClickListener {
                override fun editingGroupDailyPortionOnClickListener(quantity: Float) {
                    currentDailyPotion[index].quantity = quantity
                    checkValueIsChanged()
                }
            })
        }
    }

    private fun setValuesInNutrimentButtons(editingGroupDailyPotionList: ArrayList<EditingGroupDailyPortionView>) {
        for ((key, group) in editingGroupDailyPotionList.withIndex()) {
            val quantity = this.currentDailyPotion[key].quantity
            group.setValueInButtonNutriment(quantity)
        }
    }

    private fun setTitleInNutrimentButtons(editingGroupDailyPotionList: ArrayList<EditingGroupDailyPortionView>) {
        for ((key, group) in editingGroupDailyPotionList.withIndex()) {
            val title = nutrimentTitles[this.currentDailyPotion[key].nameId]
            group.setNutrimentTitle(title)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.save_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if(item!!.itemId == R.id.saveMenu){
            val intent = Intent()
            intent.putExtra(DayFragment.EXTRA_DAILYPORTION, this.currentDailyPotion)
            intent.putExtra(DayFragment.EXTRA_VALUE_CHANGE, valuesIsChanged)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }else{
            showAlertDialog()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun checkValueIsChanged() {
        valuesIsChanged = true
    }

    private fun showAlertDialog(){
        val alertDialog = AlertDialog.Builder(this)

        alertDialog.setTitle(
            resources.getString(R.string.cancelChanges_title
            )
        )
        alertDialog.setMessage(
            resources.getString(
                R.string.cancelChanges_message
            )
        )

        alertDialog.setPositiveButton(
            resources.getString(R.string.yes)
        ) { _, _ ->
            finish()
        }

        alertDialog.setNegativeButton(
            resources.getString(R.string.no)
        ) { _, _ -> }

        alertDialog.show()
    }
}