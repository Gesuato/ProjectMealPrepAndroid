package com.easyprep.easyprep.ui

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.easyprep.easyprep.R
import com.easyprep.easyprep.data.model.DailyMealPlanDefaultListBuilder
import com.easyprep.easyprep.data.model.Week
import com.easyprep.easyprep.ui.adapters.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var viewPager: ViewPager
    private lateinit var week : Week

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        week = Week(DailyMealPlanDefaultListBuilder().invoke())

        Log.d("SizeList", week.dailyMealPlanList[0].meals[0].toString())

        this.viewPager = findViewById(R.id.viewPagerId)
        this.viewPagerAdapter = ViewPagerAdapter(
            supportFragmentManager,
            resources.getStringArray(R.array.pagerDayTitles),
            week
        )
        this.viewPager.adapter = viewPagerAdapter
        tabLayoutId.setupWithViewPager(viewPager)
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
                intent.putExtra("SUPERMARKETLIST", week)
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

    fun getWeek(): Week = week
}