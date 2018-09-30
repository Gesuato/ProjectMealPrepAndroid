package com.easyprep.easyprep.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.easyprep.easyprep.R
import com.easyprep.easyprep.data.model.SupermarketItem
import com.easyprep.easyprep.data.model.Week
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_supermarket_list.*


class SupermarketListActivity : AppCompatActivity() {

    private lateinit var week: Week
    private var supermarketList =
        arrayListOf<ArrayList<SupermarketItem>>(
            ArrayList(),
            ArrayList(),
            ArrayList(),
            ArrayList(),
            ArrayList(),
            ArrayList(),
            ArrayList()
        )

    private val excitingSection = Section()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_supermarket_list)

        if (!::week.isInitialized) {
            week = intent.getSerializableExtra("SUPERMARKETLIST") as Week
        }
        removeDuplicateItemsInSupermarketList()

        Log.d("Quantity",supermarketList[4].size.toString())
        val groupAdapter = GroupAdapter<ViewHolder>()

        recycleView_Supermarket.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = groupAdapter
        }

        ExpandableGroup(
            ExpandableHeaderItem(
                resources.getString(R.string.veggies).capitalize(),
                R.color.vegetableColor_expandable_header
            ), true
        ).apply {
            add(Section(generateItemSupermarketListViews(0)))
            groupAdapter.add(this)
        }

        ExpandableGroup(
            ExpandableHeaderItem(
                resources.getString(R.string.fruits).capitalize(),
                R.color.fruitColor_expandable_header
            ), false
        ).apply {
            excitingSection.addAll(generateItemSupermarketListViews(1))
            add(excitingSection)
            groupAdapter.add(this)
        }

        ExpandableGroup(
            ExpandableHeaderItem(
                resources.getString(R.string.proteins).capitalize(),
                R.color.proteinColor_expandable_header
            ), false
        ).apply {
//            excitingSection.addAll()
            add(Section(generateItemSupermarketListViews(2)))
            groupAdapter.add(this)
        }

        ExpandableGroup(
            ExpandableHeaderItem(
                resources.getString(R.string.carbohydrates).capitalize(),
                R.color.carbohydrateColor_expandable_header
            ), false
        ).apply {
            excitingSection.addAll(generateItemSupermarketListViews(3))
            add(excitingSection)
            groupAdapter.add(this)
        }

        ExpandableGroup(
            ExpandableHeaderItem(
                resources.getString(R.string.healthyFats).capitalize(),
                R.color.goodFatColor_expandable_header
            ), false
        ).apply {
            excitingSection.addAll(generateItemSupermarketListViews(4))
            add(excitingSection)
            groupAdapter.add(this)
        }

        ExpandableGroup(
            ExpandableHeaderItem(
                resources.getString(R.string.seedsAndDressings).capitalize(),
                R.color.seedColor_expandable_header
            ), false
        ).apply {
            excitingSection.addAll(generateItemSupermarketListViews(5))
            add(excitingSection)
            groupAdapter.add(this)
        }

        ExpandableGroup(
            ExpandableHeaderItem(
                resources.getString(R.string.oilsAndNutButters).capitalize(),
                R.color.oilColor_expandable_header
            ), false
        ).apply {
            excitingSection.addAll(generateItemSupermarketListViews(6))
            add(excitingSection)
            groupAdapter.add(this)
        }
    }

    private fun organizingNutriments(): ArrayList<ArrayList<String>> {
        val supermarketItems = arrayListOf<ArrayList<String>>(
            ArrayList(),
            ArrayList(),
            ArrayList(),
            ArrayList(),
            ArrayList(),
            ArrayList(),
            ArrayList()
        )

        for (dailyMealPlan in week.dailyMealPlanList) {
            for (meal in dailyMealPlan.meals) {
                for ((key, nutriment) in meal.nutriments.withIndex()) {
                    if (nutriment.items.isNotEmpty() && nutriment.items.isNotBlank()) {
                        val items = nutriment.items
                        supermarketItems[key].add(items)
                    }
                }
            }
        }
        return supermarketItems
    }

    private fun splitSupermarketItems(): ArrayList<ArrayList<String>> {
        val supermarketItems = organizingNutriments()
        val supermarketItemsSplit = arrayListOf<ArrayList<String>>(
            ArrayList(),
            ArrayList(),
            ArrayList(),
            ArrayList(),
            ArrayList(),
            ArrayList(),
            ArrayList()
        )

        for ((index, list) in supermarketItems.withIndex()) {
            for (items in list) {
                val listItems = items.split(",")
                if (listItems.size > 1) {
                    for (item in listItems) {
                        val itemFormated = removeSpace(item)
                        supermarketItemsSplit[index].add(itemFormated)
                    }
                } else {
                    supermarketItemsSplit[index].add(listItems[0])
                }
            }
        }
        return supermarketItemsSplit
    }

    private fun removeDuplicateItemsInSupermarketList() {
        val supermarketItemsSplit = splitSupermarketItems()

        for ((i, list) in supermarketItemsSplit.withIndex()) {
            for ((j, itemA) in list.withIndex()) {
                var quantity = 1
                for ((key, itemB) in supermarketItemsSplit[i].withIndex()) {

                    if (itemA.equals(itemB, true) && j != key) {
                        list[key] = null.toString()
                        quantity += 1
                    }
                }
                if (itemA != "null") {
                    val supermarketItem = SupermarketItem(itemA, quantity, i)
                    this.supermarketList[i].add(supermarketItem)
                }
            }
        }
    }

    private fun removeSpace(item: String): String {
        return item.replace("\\s".toRegex(), "")
    }

    private fun generateItemSupermarketListViews(i: Int): MutableList<ItemSupermarketListView> {
        val listColors = arrayOf(
            R.color.vegetableColor_expandable_header,
            R.color.fruitColor_expandable_header,
            R.color.proteinColor_expandable_header,
            R.color.carbohydrateColor_expandable_header,
            R.color.goodFatColor_expandable_header,
            R.color.seedColor_expandable_header,
            R.color.oilColor_expandable_header
        )
        val mutableList = mutableListOf<ItemSupermarketListView>()
        for (item in supermarketList[i]) {
            val titleFormated = item.name + " (" + item.quantity.toString() + ")"
            val currentColor = listColors[item.type]
            mutableList.add(ItemSupermarketListView(titleFormated, currentColor))
        }
        return mutableList
    }
}