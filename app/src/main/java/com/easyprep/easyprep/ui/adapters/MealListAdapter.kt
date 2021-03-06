package com.easyprep.easyprep.ui.adapters

import android.content.res.Resources
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.easyprep.easyprep.R
import com.easyprep.easyprep.data.model.Meal
import com.easyprep.easyprep.ui.interfaces.CustomOnClickListener
import com.easyprep.easyprep.ui.mainActivity.MealPortionView

class MealListAdapter : RecyclerView.Adapter<MealListAdapter.MealViewHolder>() {

    private lateinit var currentMeals: List<Meal>
    private lateinit var resources: Resources
    private lateinit var onCustomClickListener: CustomOnClickListener
    private lateinit var titlesOfMeals: Array<String>

    fun update(currentMeals: List<Meal>) {
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
        resources = layoutInflater.context.resources
        if (!::titlesOfMeals.isInitialized) {
            this.titlesOfMeals = resources.getStringArray(R.array.mealTitles)
        }

        viewHolder.imageButtonEdit.setOnClickListener {
            onCustomClickListener.onCustomItemClickListener(currentMeals[viewHolder.adapterPosition])
        }


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
        holder.titleMeal.text = titlesOfMeals[currentMeal.nameId]
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