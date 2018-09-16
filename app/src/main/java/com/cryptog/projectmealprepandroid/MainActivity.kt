package com.cryptog.projectmealprepandroid

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.cryptog.projectmealprepandroid.data.model.Day
import com.cryptog.projectmealprepandroid.data.model.Week
import com.cryptog.projectmealprepandroid.data.model.adapters.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: ViewPagerAdapter
    private val week = Week()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = ViewPagerAdapter(
            supportFragmentManager,
            resources.getStringArray(R.array.pagerDayTitles)
        )
        viewPagerId.adapter = adapter
        tabLayoutId.setupWithViewPager(viewPagerId)
    }

    fun getCurrentDay(): Day {
        return week.days[viewPagerId.currentItem]
    }

}
