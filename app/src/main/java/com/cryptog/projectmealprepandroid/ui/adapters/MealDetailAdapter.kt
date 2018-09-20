package com.cryptog.projectmealprepandroid.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cryptog.projectmealprepandroid.R

class MealDetailAdapter : RecyclerView.Adapter<MealDetailAdapter.MealDetailHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): MealDetailHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val itemView = layoutInflater.inflate(R.layout.meal_detail_view_holder, viewGroup, false)
        val viewGroup = MealDetailHolder(itemView)

        return viewGroup
    }

    override fun getItemCount(): Int {
        return 7
    }

    override fun onBindViewHolder(p0: MealDetailHolder, p1: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    class MealDetailHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}