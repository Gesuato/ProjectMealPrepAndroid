package com.cryptog.projectmealprepandroid

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.cryptog.projectmealprepandroid.data.model.Day
import com.cryptog.projectmealprepandroid.data.model.Week
import com.cryptog.projectmealprepandroid.data.model.adapters.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private val week = Week()
    private lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager = findViewById(R.id.viewPagerId)
        viewPagerAdapter = ViewPagerAdapter(
            supportFragmentManager,
            resources.getStringArray(R.array.pagerDayTitles)
        )
        viewPager!!.adapter = viewPagerAdapter
        tabLayoutId.setupWithViewPager(viewPager)
    }

    fun getCurrentDay(): Day {
        Log.d("CurrentDay", resources.getText(week.days[tabLayoutId.selectedTabPosition].nameId).toString())
        return week.days[tabLayoutId.selectedTabPosition]
    }

}
