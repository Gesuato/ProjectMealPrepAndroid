package com.easyprep.easyprep.ui.adapters

import android.content.res.Resources
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.easyprep.easyprep.R
import com.easyprep.easyprep.data.model.Meal

class MealDetailAdapter : RecyclerView.Adapter<MealDetailAdapter.MealDetailHolder>() {

    private lateinit var currentMeal: Meal
    private lateinit var resources: Resources
    private lateinit var nutrimentTitles : Array<String>
    private lateinit var hints : Array<String>
    private val backgroundBtns =
        arrayOf(
            R.drawable.background_circle_btn_vegetable,
            R.drawable.background_circle_btn_fruit,
            R.drawable.background_circle_btn_protein,
            R.drawable.background_circle_btn_carbohydrate,
            R.drawable.background_circle_btn_goodfat,
            R.drawable.background_circle_btn_seed,
            R.drawable.background_circle_btn_oil
        )

    fun update(currentMeal: Meal) {
        this.currentMeal = currentMeal
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): MealDetailHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val itemView = layoutInflater.inflate(R.layout.meal_detail_view_holder, viewGroup, false)
        val viewHolder = MealDetailHolder(itemView)
        this.resources = layoutInflater.context.resources

        if(!::nutrimentTitles.isInitialized){
            this.nutrimentTitles = resources.getStringArray(R.array.nutrimentTitles)
        }

        if(!::hints.isInitialized){
            this.hints = resources.getStringArray(R.array.hintsMealDescription)
        }

        viewHolder.btnPlus.setOnClickListener {
            viewHolder.btnQuantityNutriment.text =
                    btnPlusOrLessIsTouched("PLUS", viewHolder.adapterPosition)
        }

        viewHolder.btnLess.setOnClickListener {
            viewHolder.btnQuantityNutriment.text =
                    btnPlusOrLessIsTouched("LESS", viewHolder.adapterPosition)
        }

        viewHolder.editextNutriment.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                currentMeal.nutriments[viewHolder.adapterPosition].items =
                        viewHolder.editextNutriment.text.toString()
                valuesIsChanged()
            }

            override fun beforeTextChanged(
                caracter: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {

            }

            override fun onTextChanged(p0: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        return viewHolder
    }

    override fun getItemCount(): Int {
        return 7
    }

    override fun onBindViewHolder(holder: MealDetailHolder, position: Int) {
        holder.btnQuantityNutriment.setBackgroundResource(backgroundBtns[position])
        holder.textViewNutrimentName.text = nutrimentTitles[currentMeal.nutriments[position].nameId]
        holder.btnQuantityNutriment.text = currentMeal.nutriments[position].quantity.toString()
        holder.editextNutriment.hint = hints[position]
        holder.editextNutriment.setText(currentMeal.nutriments[position].items)

    }

    class MealDetailHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val btnPlus = itemView.findViewById<Button>(R.id.btnPlus)!!
        val btnLess = itemView.findViewById<Button>(R.id.btnLess)!!
        val btnQuantityNutriment = itemView.findViewById<Button>(R.id.btnNutriment)!!
        val textViewNutrimentName = itemView.findViewById<TextView>(R.id.textViewNutriment)!!
        val editextNutriment = itemView.findViewById<EditText>(R.id.editTextMarketList)!!
    }

    private fun getQuantity(position: Int): String {
        return currentMeal.nutriments[position].quantity.toString()
    }

    private fun valuesIsChanged() {
        if (!currentMeal.valueIsChanged) {
            currentMeal.valueIsChanged = true
        }
    }

    private fun btnPlusOrLessIsTouched(type: String, index: Int): String {
        val quantityChanged = 0.5f
        var currentQuantity = currentMeal.nutriments[index].quantity
        if (type == "PLUS") {
            currentQuantity += quantityChanged
        } else if (type == "LESS") {
            if (currentQuantity > 0) {
                currentQuantity -= quantityChanged
            }
        }
        currentMeal.nutriments[index].quantity = currentQuantity
        valuesIsChanged()
        return getQuantity(index)
    }
}