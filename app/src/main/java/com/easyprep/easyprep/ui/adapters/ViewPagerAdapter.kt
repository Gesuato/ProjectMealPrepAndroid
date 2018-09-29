package com.easyprep.projectmealprepandroid.ui.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.easyprep.projectmealprepandroid.data.model.Week
import com.easyprep.projectmealprepandroid.ui.DayFragment

class ViewPagerAdapter(fm: FragmentManager?, private val titlesTab: Array<String>, private val week: Week) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return DayFragment.newInstance(week.dailyMealPlanList[position])
    }

    override fun getCount(): Int {
        return titlesTab.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titlesTab[position]
    }
}