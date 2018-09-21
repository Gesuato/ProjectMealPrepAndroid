package com.cryptog.projectmealprepandroid.ui.adapters

import android.content.res.Resources
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.cryptog.projectmealprepandroid.R
import com.cryptog.projectmealprepandroid.data.model.DailyMealPlan
import com.cryptog.projectmealprepandroid.ui.CustomOnClickListener
import com.cryptog.projectmealprepandroid.ui.MealPortionView

class MealListAdapter : RecyclerView.Adapter<MealListAdapter.MealViewHolder>() {

    private var currentDay = DailyMealPlan()
    private lateinit var resources: Resources
    private lateinit var onCustomClickListener: CustomOnClickListener
    private lateinit var mealPortionView: MealPortionView

    fun update(currentDailyMealPlan: DailyMealPlan) {
        this.currentDay = currentDailyMealPlan
        notifyDataSetChanged()
    }

    fun updateItemChanged(currentDailyMealPlan: DailyMealPlan, index: Int) {
        this.currentDay = currentDailyMealPlan
        notifyItemChanged(index)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): MealViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val itemView = layoutInflater.inflate(R.layout.meal_view_holder, viewGroup, false)
        mealPortionView = itemView.findViewById(R.id.mealPortionViewId)
        val viewHolder = MealViewHolder(itemView)

        resources = layoutInflater.context.resources
        return viewHolder
    }

    override fun getItemCount(): Int {
        return if (!currentDay.meals.isEmpty()) {
            currentDay.meals.size
        } else {
            0
        }
    }

    override fun onBindViewHolder(holder: MealViewHolder, p1: Int) {
        holder.titleMeal.text = resources.getText(currentDay.meals[p1].nameId)
        holder.imageButtonEdit.setOnClickListener {
            onCustomClickListener.onCustomItemClickListener(currentDay.meals[holder.adapterPosition])
        }
        mealPortionView.setValue(currentDay.meals[holder.adapterPosition])
    }

    fun setOnCustomItemClickListener(onItemClickListener: CustomOnClickListener) {
        onCustomClickListener = onItemClickListener
    }

    class MealViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleMeal = itemView.findViewById<TextView>(R.id.titleMealId)!!
        val imageButtonEdit = itemView.findViewById<ImageButton>(R.id.imageButtonEdit)!!
    }
}