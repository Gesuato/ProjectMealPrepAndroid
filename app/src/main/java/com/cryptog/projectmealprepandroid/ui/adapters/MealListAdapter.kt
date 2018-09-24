package com.cryptog.projectmealprepandroid.ui.adapters

import android.content.res.Resources
import android.support.v7.widget.RecyclerView
import android.util.Log
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

    private var currentDailyMealPlan = DailyMealPlan()
    private lateinit var resources: Resources
    private lateinit var onCustomClickListener: CustomOnClickListener

    fun update(currentDailyMealPlan: DailyMealPlan) {
        this.currentDailyMealPlan = currentDailyMealPlan
        notifyDataSetChanged()
    }

    fun updateItemChanged(currentDailyMealPlan: DailyMealPlan, index: Int) {
        this.currentDailyMealPlan = currentDailyMealPlan
        notifyItemChanged(index)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MealViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val itemView = layoutInflater.inflate(R.layout.meal_view_holder, viewGroup, false)
        val viewHolder = MealViewHolder(itemView)

        viewHolder.imageButtonEdit.setOnClickListener{
            onCustomClickListener.onCustomItemClickListener(currentDailyMealPlan.meals[viewHolder.adapterPosition])
        }

        resources = layoutInflater.context.resources
        return viewHolder
    }

    override fun getItemCount(): Int {
        return if (!currentDailyMealPlan.meals.isEmpty()) {
            currentDailyMealPlan.meals.size
        } else {
            0
        }
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val currentMeal = currentDailyMealPlan.meals[position]
        holder.titleMeal.text = resources.getText(currentMeal.nameId)
        holder.mealPortionView.setValues(currentMeal)
    }

    fun setOnCustomItemClickListener(onItemClickListener: CustomOnClickListener) {
        onCustomClickListener = onItemClickListener
    }

    class MealViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleMeal = itemView.findViewById<TextView>(R.id.titleMealId)!!
        val imageButtonEdit = itemView.findViewById<ImageButton>(R.id.imageButtonEdit)!!
        val mealPortionView = itemView.findViewById<MealPortionView>(R.id.mealPortionViewId)
    }
}