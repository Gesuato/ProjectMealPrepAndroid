package com.cryptog.projectmealprepandroid.ui


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cryptog.projectmealprepandroid.R
import com.cryptog.projectmealprepandroid.data.model.DailyMealPlan
import com.cryptog.projectmealprepandroid.ui.adapters.MealListAdapter


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class DayFragment : Fragment() {

    private lateinit var currentDailyMealPlan: DailyMealPlan
    private lateinit var recyclerView: RecyclerView
    private var mealListAdapter = MealListAdapter()

    private lateinit var dailyPortionView: DailyPortionView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentDailyMealPlan = (activity as MainActivity).getCurrentDay()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_day, container, false)
        recyclerView = rootView.findViewById(R.id.recycleViewId) as RecyclerView

        dailyPortionView = rootView.findViewById<DailyPortionView>(R.id.daily_portion)
        var layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        mealListAdapter.update(currentDailyMealPlan)
        recyclerView.adapter = mealListAdapter

        dailyPortionView.setValuesInPortionList(currentDailyMealPlan)

        return rootView
    }

    override fun onResume() {
        super.onResume()

        currentDailyMealPlan = (activity as MainActivity).getCurrentDay()
        mealListAdapter.update(currentDailyMealPlan)
        dailyPortionView.setValuesInPortionList(currentDailyMealPlan)
    }
}
