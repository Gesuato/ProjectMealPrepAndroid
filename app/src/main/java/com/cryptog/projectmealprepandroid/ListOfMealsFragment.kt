package com.cryptog.projectmealprepandroid


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cryptog.projectmealprepandroid.data.model.Day
import com.cryptog.projectmealprepandroid.data.model.adapters.MealListAdapter


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class ListOfMealsFragment : Fragment() {

    private var currentDay = Day()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentDay = (parentFragment as DayFragment).getCurrentDay()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_list_of_meals, container, false)

        val recyclerView = rootView.findViewById(R.id.recycleViewId) as RecyclerView
        var layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        var mealListAdapter = MealListAdapter()
        mealListAdapter.update(currentDay!!)
        recyclerView.adapter = mealListAdapter

        Log.d("CurrentDay", resources.getText(currentDay.nameId).toString())
        return rootView
    }
}