package com.cryptog.projectmealprepandroid


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.cryptog.projectmealprepandroid.data.model.Day

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class DailyPortionsFragment : Fragment() {

    private val dailyPortionsIds = arrayOf(
        R.id.tVQuantityVegetables,
        R.id.tVQuantityFruits,
        R.id.tVQuantityProteins,
        R.id.tVQuantityCarbohydrates,
        R.id.tVQuantityGoodFat,
        R.id.tVQuantitySeeds,
        R.id.tVQuantityOils
    )
    private val dailyPortions = ArrayList<TextView>()
    private lateinit var currentDay: Day

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentDay = (parentFragment as DayFragment).getCurrentDay()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_daily_portions, container, false)
        setIdsInPortionList(rootView)
        setValuesInPortionList()

        return rootView
    }

    fun setIdsInPortionList(view: View) {
        for (id in dailyPortionsIds) {
            dailyPortions.add(view.findViewById(id))
        }
    }

    fun setValuesInPortionList() {
        for ((key, value) in currentDay!!.dailyPortions.withIndex()) {
            dailyPortions[key].text = value.quantity.toString()
        }
    }
}