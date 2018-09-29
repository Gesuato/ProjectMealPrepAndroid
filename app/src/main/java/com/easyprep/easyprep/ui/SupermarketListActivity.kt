package com.easyprep.easyprep.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.easyprep.easyprep.R
import com.easyprep.easyprep.data.model.SupermarketItem
import com.easyprep.easyprep.data.model.Week


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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_supermarket_list)

        if (!::week.isInitialized) {
            week = intent.getSerializableExtra("SUPERMARKETLIST") as Week
        }
        removeDuplicateItemsInSupermarketList()
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
                        supermarketItemsSplit[index].add(item)
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
                        list.removeAt(key)
                        quantity += 1
                    }
                }
                val supermarketItem = SupermarketItem(itemA, quantity)
                this.supermarketList[i].add(supermarketItem)
            }
        }
    }
}