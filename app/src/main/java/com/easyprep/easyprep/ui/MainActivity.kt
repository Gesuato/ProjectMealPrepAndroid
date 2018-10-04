package com.easyprep.easyprep.ui

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.easyprep.easyprep.R
import com.easyprep.easyprep.data.model.DailyMealPlanDefaultListBuilder
import com.easyprep.easyprep.data.model.WeekMealPlan
import com.easyprep.easyprep.ui.adapters.ViewPagerAdapter
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var viewPager: ViewPager
    private var weekMealPlan = WeekMealPlan()
    private val db = FirebaseFirestore.getInstance()
    private val weekRef = db.collection("WeekMealPlan").document(USERID)

    companion object {
        const val USERID = "victorgesuato4@gmail.com"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.viewPager = findViewById(R.id.viewPagerId)
        loadData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val intent: Intent

        when (item!!.itemId) {
            R.id.supermarketListMenu -> {
                intent = Intent(this, SupermarketListActivity::class.java)
                intent.putExtra("SUPERMARKETLIST", weekMealPlan)
                startActivity(intent)
                finish()
            }
            R.id.introductionMenu -> {

            }
            R.id.contactMenu -> {

            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun getWeek(): WeekMealPlan = weekMealPlan

    fun saveData(weekMealPlan: WeekMealPlan) {
        weekRef.set(weekMealPlan)
    }

    private fun loadData() {

        weekMealPlan.dailyMealPlanList = DailyMealPlanDefaultListBuilder().invoke()

        weekRef.get().addOnSuccessListener { documentSnapshot ->
            var saveData = false
            if (documentSnapshot.exists()) {
                weekMealPlan = documentSnapshot.toObject(WeekMealPlan::class.java)!!

            } else {
                saveData = true
            }
            if (weekMealPlan.dailyMealPlanList.isNotEmpty()) {
                this.viewPagerAdapter = ViewPagerAdapter(
                    supportFragmentManager,
                    resources.getStringArray(R.array.pagerDayTitles),
                    weekMealPlan
                )
                this.viewPager.adapter = this.viewPagerAdapter
                tabLayoutId.setupWithViewPager(viewPager)
            }

            progressBarLoadData.visibility = View.GONE

            if (saveData) {
                saveData(weekMealPlan)
            }
        }.addOnFailureListener {

        }
    }
}
