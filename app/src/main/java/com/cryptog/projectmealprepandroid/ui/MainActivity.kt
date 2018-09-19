package com.cryptog.projectmealprepandroid.ui

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.cryptog.projectmealprepandroid.R
import com.cryptog.projectmealprepandroid.data.model.DailyMealPlan
import com.cryptog.projectmealprepandroid.data.model.Week
import com.cryptog.projectmealprepandroid.ui.adapters.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private val week = Week()
    private lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.viewPager = findViewById(R.id.viewPagerId)
        this.viewPagerAdapter = ViewPagerAdapter(
            supportFragmentManager,
            resources.getStringArray(R.array.pagerDayTitles)
        )
        this.viewPager.adapter = viewPagerAdapter
        tabLayoutId.setupWithViewPager(viewPager)
    }

    fun getCurrentDay(): DailyMealPlan {
        Log.d(
            "CurrentDay",
            resources.getText(week.days[this.viewPager.currentItem].day) as String?
        )
        return week.days[this.viewPager.currentItem]
    }
}