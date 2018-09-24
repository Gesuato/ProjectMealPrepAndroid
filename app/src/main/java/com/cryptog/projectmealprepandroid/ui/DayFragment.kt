package com.cryptog.projectmealprepandroid.ui


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cryptog.projectmealprepandroid.R
import com.cryptog.projectmealprepandroid.data.model.DailyMealPlan
import com.cryptog.projectmealprepandroid.data.model.Meal
import com.cryptog.projectmealprepandroid.ui.adapters.MealListAdapter
import kotlinx.android.synthetic.main.daily_portion_view.view.*


class DayFragment : Fragment() {

    private lateinit var currentDailyMealPlan: DailyMealPlan
    private lateinit var recyclerView: RecyclerView
    private val mealListAdapter = MealListAdapter()
    private lateinit var dailyPortionView: DailyPortionView

    companion object {
        const val ARG_CAUGHT = "DayFragment"
        const val REQUEST_INSERT = 0
        const val EXTRA_MEAL = "CURRENT_MEAL"

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
        val layoutManager = LinearLayoutManager(context)

        if (!::currentDailyMealPlan.isInitialized) {
            currentDailyMealPlan = arguments!!.getSerializable(ARG_CAUGHT) as DailyMealPlan
        }

        this.recyclerView = rootView.findViewById(R.id.recycleViewId) as RecyclerView
        this.dailyPortionView = rootView.findViewById(R.id.daily_portion)

        this.recyclerView.layoutManager = layoutManager
        this.mealListAdapter.update(this.currentDailyMealPlan)
        this.recyclerView.adapter = mealListAdapter

        this.dailyPortionView.setValuesInPortionList(this.currentDailyMealPlan)
        this.dailyPortionView.imgBtnDetailEdit.setOnClickListener {
            val intent = Intent(context,DetailDailyPortionActivity::class.java)
            startActivity(intent)
        }
        this.mealListAdapter.setOnCustomItemClickListener(object : CustomOnClickListener {
            override fun onCustomItemClickListener(meal: Meal) {
                val intent = Intent(context, DetailMealPortionActivity::class.java)
                intent.putExtra(EXTRA_MEAL, meal)
                startActivityForResult(intent, REQUEST_INSERT)
            }
        })
        return rootView
    }

    override fun onResume() {
        super.onResume()
        this.mealListAdapter.update(this.currentDailyMealPlan)
        this.dailyPortionView.setValuesInPortionList(this.currentDailyMealPlan)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_INSERT && resultCode == Activity.RESULT_OK) {
            val meal = data?.getSerializableExtra(EXTRA_MEAL) as Meal
            val index = meal.id
            this.currentDailyMealPlan.meals[index] = meal
            this.mealListAdapter.updateItemChanged(this.currentDailyMealPlan, index)
        }
    }
}
