package com.cryptog.projectmealprepandroid.ui


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cryptog.projectmealprepandroid.R
import com.cryptog.projectmealprepandroid.data.model.DailyMealPlan
import com.cryptog.projectmealprepandroid.data.model.Meal
import com.cryptog.projectmealprepandroid.ui.adapters.MealListAdapter


class DayFragment : Fragment() {

    private lateinit var currentDailyMealPlan: DailyMealPlan
    private lateinit var recyclerView: RecyclerView
    private var mealListAdapter = MealListAdapter()
    private lateinit var dailyPortionView: DailyPortionView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        currentDailyMealPlan = arguments!!.getSerializable(ARG_CAUGHT) as DailyMealPlan
    }

    companion object {
        private const val ARG_CAUGHT = "DayFragment"
        val REQUEST_INSERT = 0
        val EXTRA_MEAL = "CURRENT_MEAL"

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

        recyclerView = rootView.findViewById(R.id.recycleViewId) as RecyclerView
        dailyPortionView = rootView.findViewById(R.id.daily_portion)

        recyclerView.layoutManager = layoutManager
        mealListAdapter.update(currentDailyMealPlan)
        recyclerView.adapter = mealListAdapter

        dailyPortionView.setValuesInPortionList(currentDailyMealPlan)
        mealListAdapter.setOnCustomItemClickListener(object : CustomOnClickListener {
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
        currentDailyMealPlan = arguments!!.getSerializable(ARG_CAUGHT) as DailyMealPlan
        mealListAdapter.update(currentDailyMealPlan)
        dailyPortionView.setValuesInPortionList(currentDailyMealPlan)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_INSERT && resultCode == Activity.RESULT_OK) {
            val meal = data?.getSerializableExtra(EXTRA_MEAL) as Meal
            val index = meal.id
            Log.d("Index",index.toString())
            if (meal != null) {
                currentDailyMealPlan.meals[index] = meal
                mealListAdapter.updateItemChanged(currentDailyMealPlan,index)
//                recyclerView.adapter = mealListAdapter
            }
        }
    }
}
