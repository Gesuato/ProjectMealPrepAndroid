package com.cryptog.projectmealprepandroid.ui.adapters

import android.content.res.Resources
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.cryptog.projectmealprepandroid.R
import com.cryptog.projectmealprepandroid.data.model.Meal

class MealDetailAdapter : RecyclerView.Adapter<MealDetailAdapter.MealDetailHolder>() {

    private lateinit var currentMeal: Meal
    private lateinit var resources : Resources

    fun update(currentMeal: Meal){
        this.currentMeal = currentMeal
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): MealDetailHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val itemView = layoutInflater.inflate(R.layout.meal_detail_view_holder, viewGroup, false)
        val viewGroup = MealDetailHolder(itemView)

        resources = layoutInflater.context.resources

        return viewGroup
    }

    override fun getItemCount(): Int {
        return 7
    }

    override fun onBindViewHolder(holder: MealDetailHolder, position: Int) {

        var currentQuantity = currentMeal.nutriments[position].quantity
        holder.textViewNutrimentName.text = resources.getText(currentMeal.nutriments[position].nameId)
        holder.btnQuantityNutriment.text = currentMeal.nutriments[position].quantity.toString()

        holder.btnPlus.setOnClickListener {
            currentQuantity += 0.5f
            currentMeal.nutriments[position].quantity = currentQuantity
            holder.btnQuantityNutriment.text = setQuantity(position)
        }

        holder.btnLess.setOnClickListener{
            if(currentQuantity > 0){
                currentQuantity -= 0.5f
                currentMeal.nutriments[position].quantity = currentQuantity
                holder.btnQuantityNutriment.text = setQuantity(position)
            }
        }
    }

    class MealDetailHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val btnPlus = itemView.findViewById<Button>(R.id.btnPlus)!!
        val btnLess = itemView.findViewById<Button>(R.id.btnLess)!!
        val btnQuantityNutriment = itemView.findViewById<Button>(R.id.btnNutriment)!!
        val textViewNutrimentName = itemView.findViewById<TextView>(R.id.textViewNutriment)!!
    }

    private fun setQuantity(position: Int) : String{
        return currentMeal.nutriments[position].quantity.toString()
    }
}