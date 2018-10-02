package com.easyprep.easyprep.ui

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.easyprep.easyprep.R
import com.easyprep.easyprep.data.model.DailyMealPlan
import com.easyprep.easyprep.data.model.Meal
import com.easyprep.easyprep.data.model.Nutriment
import com.easyprep.easyprep.data.model.Week
import com.easyprep.easyprep.ui.adapters.MealListAdapter
import kotlinx.android.synthetic.main.custom_popup.*
import kotlinx.android.synthetic.main.daily_portion_view.view.*
import kotlinx.android.synthetic.main.item_popup.view.*

class DayFragment : Fragment() {

    private lateinit var currentDailyMealPlan: DailyMealPlan
    private lateinit var recyclerView: RecyclerView
    private val mealListAdapter = MealListAdapter()
    private lateinit var dailyPortionView: DailyPortionView
    private var quantityList = arrayListOf(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
    private var valuesInCurrentDailyMealPlanIsChanged = false
    private lateinit var popupCopy: Dialog

    companion object {
        const val ARG_CAUGHT = "DayFragment"
        const val REQUEST_INSERT = 0
        const val REQUEST_DAILYPORTION = 3
        const val EXTRA_MEAL = "CURRENT_MEAL"
        const val EXTRA_DAILYPORTION = "CURRENT_DAILYPORTION"
        const val EXTRA_VALUE_CHANGE = "VALUE_CHANGE"

        fun newInstance(dailyMealPlan: DailyMealPlan): DayFragment {
            val args = Bundle()
            val fragment = DayFragment()
            args.putSerializable(ARG_CAUGHT, dailyMealPlan)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_day, container, false)
        val layoutManager = LinearLayoutManager(this.context)
        this.popupCopy = Dialog(this.context)

        if (!::currentDailyMealPlan.isInitialized) {
            this.currentDailyMealPlan = arguments!!.getSerializable(ARG_CAUGHT) as DailyMealPlan
        }

        this.recyclerView = rootView.findViewById(R.id.recycleViewId) as RecyclerView
        this.dailyPortionView = rootView.findViewById(R.id.daily_portion)

        this.recyclerView.layoutManager = layoutManager
        this.mealListAdapter.update(this.currentDailyMealPlan.meals)
        this.recyclerView.adapter = mealListAdapter
        updateValuesInDailyPortionView()

        this.dailyPortionView.imgBtnDetailEdit.setOnClickListener {
            val intent = Intent(context, DetailDailyPortionActivity::class.java)
            val curentDailyPotion = this.currentDailyMealPlan.dailyPortions
            intent.putExtra(EXTRA_DAILYPORTION, curentDailyPotion)
            intent.putExtra("CURRENTDAY", currentDailyMealPlan.day)
            startActivityForResult(intent, REQUEST_DAILYPORTION)
        }

        this.mealListAdapter.setOnCustomItemClickListener(object : CustomOnClickListener {
            override fun onCustomItemClickListener(meal: Meal) {
                val intent = Intent(context, DetailMealPortionActivity::class.java)
                intent.putExtra(EXTRA_MEAL, meal)
                startActivityForResult(intent, REQUEST_INSERT)
            }
        })
        this.dailyPortionView.imgBtnCopy.setOnClickListener {
            showPopupCopy()
        }


        return rootView
    }

    override fun onResume() {
        super.onResume()
        if (!valuesInCurrentDailyMealPlanIsChanged) {
            this.mealListAdapter.update(this.currentDailyMealPlan.meals)
            updateValuesInDailyPortionView()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_INSERT && resultCode == Activity.RESULT_OK) {
            val meal = data?.getSerializableExtra(EXTRA_MEAL) as Meal
            val index: Int
            this.valuesInCurrentDailyMealPlanIsChanged = meal.valueIsChanged
            meal.valueIsChanged = false
            index = meal.id
            this.currentDailyMealPlan.meals[index] = meal
            messageUpdate()
            this.mealListAdapter.updateItemChanged(currentDailyMealPlan.meals, index)
            updateValuesInDailyPortionView()
        }

        if (requestCode == REQUEST_DAILYPORTION && resultCode == Activity.RESULT_OK) {
            val dailyPortions =
                data?.getSerializableExtra(EXTRA_DAILYPORTION) as ArrayList<Nutriment>
            this.valuesInCurrentDailyMealPlanIsChanged =
                    data.getBooleanExtra(EXTRA_VALUE_CHANGE, false)
            this.currentDailyMealPlan.dailyPortions = dailyPortions
            messageUpdate()
            updateValuesInDailyPortionView()
        }
    }

    private fun updateValuesInDailyPortionView() {
        setValueInQuantityList()

        for (currentMeal in currentDailyMealPlan.meals) {
            for ((key, currentNutriment) in currentMeal.nutriments.withIndex()) {
                val currentQuantity =
                    this.quantityList[key] - currentNutriment.quantity
                this.quantityList[key] = currentQuantity
            }
        }
        this.dailyPortionView.setValuesInPortionList(quantityList)
    }

    private fun setValueInQuantityList() {
        for ((key, currentNutriment) in currentDailyMealPlan.dailyPortions.withIndex()) {
            quantityList[key] = currentNutriment.quantity
        }
    }

    private fun Context.toast(message: CharSequence) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    private fun messageUpdate() {
        return context!!.toast(resources.getString(R.string.updateSuccessful))
    }

    private fun showPopupCopy() {
        val week = (activity as MainActivity).getWeek()
        val dailyMealPlansForCopy = getItemsToShowPopup(week)

        popupCopy.setContentView(R.layout.custom_popup)
        val itemsPopup =
            arrayOf(
                popupCopy.item_popup1,
                popupCopy.item_popup2,
                popupCopy.item_popup3,
                popupCopy.item_popup4,
                popupCopy.item_popup5,
                popupCopy.item_popup6
            )

        popupCopy.btnCancelPopup.setOnClickListener {
            popupCopy.dismiss()
        }
        for ((key, dailyMealPlan) in dailyMealPlansForCopy.withIndex()) {
            itemsPopup[key].setTitleItemPopup(resources.getString(dailyMealPlan.day))
        }

        (0..5).forEach { index ->
            itemsPopup[index].imgBtnCopyDailyMealPlanId.setOnClickListener {
                createAlert(dailyMealPlansForCopy[index])
            }
        }

        popupCopy.show()
    }

    private fun getItemsToShowPopup(
        week: Week
    ): ArrayList<DailyMealPlan> {

        val dailyMealPlans = ArrayList<DailyMealPlan>()
        for (dailyMealPlan in week.dailyMealPlanList) {
            if (dailyMealPlan.day != this.currentDailyMealPlan.day) {
                val cloneDailyMealPlan = dailyMealPlan.customClone()
                dailyMealPlans.add(cloneDailyMealPlan)
            }
        }
        return dailyMealPlans
    }

    private fun updateInfosByPopup(dailyMealPlansForChange: DailyMealPlan) {
        currentDailyMealPlan.meals = dailyMealPlansForChange.meals
        currentDailyMealPlan.dailyPortions = dailyMealPlansForChange.dailyPortions
        updateValuesInDailyPortionView()
        this.mealListAdapter.update(this.currentDailyMealPlan.meals)
        popupCopy.dismiss()
    }

    private fun createAlert(dayForCopy: DailyMealPlan) {
        val alertDialog = AlertDialog.Builder(context)

        alertDialog.setTitle(
            resources.getString(
                R.string.copy,
                resources.getString(dayForCopy.day)
            )
        )
        alertDialog.setMessage(
            resources.getString(
                R.string.alert_message,
                resources.getString(dayForCopy.day)
            )
        )

        alertDialog.setPositiveButton(
            resources.getString(R.string.yes)
        ) { _, _ ->
            updateInfosByPopup(dayForCopy)
            messageUpdate()
        }

        alertDialog.setNegativeButton(
            resources.getString(R.string.no)
        ) { _, _ -> }

        alertDialog.show()
    }
}