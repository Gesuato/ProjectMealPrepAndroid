package com.cryptog.projectmealprepandroid.data.model.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.cryptog.projectmealprepandroid.DayFragment

class ViewPagerAdapter(fm: FragmentManager?, private val titlesTab: Array<String>) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return DayFragment()
    }

    override fun getCount(): Int {
        return titlesTab.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titlesTab[position]
    }

}