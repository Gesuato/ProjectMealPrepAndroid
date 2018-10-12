package com.easyprep.easyprep.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.easyprep.easyprep.R
import com.easyprep.easyprep.data.model.Meal
import com.easyprep.easyprep.ui.adapters.MealDetailAdapter
import com.easyprep.easyprep.ui.mainActivity.DayFragment
import kotlinx.android.synthetic.main.activity_detail_meal_portion.*

class DetailMealPortionActivity : AppCompatActivity() {

    private lateinit var currentMeal: Meal
    private var metalDetailAdapter = MealDetailAdapter()
    private lateinit var mealTitles: Array<String>

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.save_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item!!.itemId == R.id.saveMenu) {
            val intent = Intent()
            intent.putExtra(DayFragment.EXTRA_MEAL, this.currentMeal)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }else{
            showAlertDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showAlertDialog() {
        val alertDialog = AlertDialog.Builder(this)

        alertDialog.setTitle(
            resources.getString(
                R.string.cancelChanges_title
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