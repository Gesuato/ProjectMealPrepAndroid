package com.easyprep.projectmealprepandroid.ui.adapters

import android.content.res.Resources
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.easyprep.projectmealprepandroid.R
import com.easyprep.projectmealprepandroid.data.model.DailyMealPlan
import com.easyprep.projectmealprepandroid.data.model.Meal
import com.easyprep.projectmealprepandroid.ui.CustomOnClickListener
import com.easyprep.projectmealprepandroid.ui.MealPortionView

class MealListAdapter : RecyclerView.Adapter<MealListAdapter.MealViewHolder>() {

    private var currentMeals = ArrayList<Meal>()
    private lateinit var resources: Resources
    private lateinit var onCustomClickListener: CustomOnClickListener

    fun update(currentMeals: ArrayList<Meal>) {
        this.currentMeals = currentMeals
        notifyDataSetChanged()
    }

    fun updateItemChanged(currentMeals: ArrayList<Meal>, index: Int) {
        this.currentMeals = currentMeals
        notifyItemChanged(index)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MealViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val itemView = layoutInflater.inflate(R.layout.meal_view_holder, viewGroup, false)
        val viewHolder = MealViewHolder(itemView)

        viewHolder.imageButtonEdit.setOnClickListener{
            onCustomClickListener.onCustomItemClickListener(currentMeals[viewHolder.adapterPosition])
        }

        resources = layoutInflater.context.resources
        return viewHolder
    }

    override fun getItemCount(): Int {
        return if (!currentMeals.isEmpty()) {
            currentMeals.size
        } else {
            0
        }
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val currentMeal = currentMeals[position]
        holder.titleMeal.text = resources.getText(currentMeal.nameId)
        holder.mealPortionView.setValues(currentMeal)
    }

    fun setOnCustomItemClickListener(onItemClickListener: CustomOnClickListener) {
        onCustomClickListener = onItemClickListener
    }

    class MealViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleMeal = itemView.findViewById<TextView>(R.id.titleMealId)!!
        val imageButtonEdit = itemView.findViewById<ImageButton>(R.id.imageButtonEdit)!!
        val mealPortionView = itemView.findViewById<MealPortionView>(R.id.mealPortionViewId)!!
    }
}