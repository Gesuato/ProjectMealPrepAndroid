package com.cryptog.projectmealprepandroid

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class ViewPagerAdapter(fm: FragmentManager?, val titlesTab: Array<String>) :
    FragmentPagerAdapter(fm) {

    override fun getItem(p0: Int): Fragment {
        return DayFragment()
    }

    override fun getCount(): Int {
        return titlesTab.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titlesTab[position]
    }
}