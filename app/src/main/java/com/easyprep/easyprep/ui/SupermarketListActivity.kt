package com.easyprep.easyprep.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.easyprep.easyprep.R
import com.easyprep.easyprep.data.model.SupermarketItem
import com.easyprep.easyprep.data.model.WeekMealPlan
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_supermarket_list.*


class SupermarketListActivity : AppCompatActivity() {

    private lateinit var week: WeekMealPlan
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

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_supermarket_list)
        supportActionBar!!.run {
            setDefaultDisplayHomeAsUpEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            title = resources.getString(R.string.supermarketList)
        }

        if (!::week.isInitialized) {
            week = intent.getSerializableExtra("SUPERMARKETLIST") as WeekMealPlan
        }
        removeDuplicateItemsInSupermarketList()

        val groupAdapter = GroupAdapter<ViewHolder>()

        recycleView_Supermarket.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = groupAdapter
        }

        ExpandableGroup(
            ExpandableHeaderItem(
                resources.getString(R.string.veggies).capitalize()
            ), true
        ).apply {
            add(Section(generateItemSupermarketListViews(0)))
            groupAdapter.add(this)
        }

        ExpandableGroup(
            ExpandableHeaderItem(
                resources.getString(R.string.fruits).capitalize()
            ), false
        ).apply {
            add(Section(generateItemSupermarketListViews(1)))
            groupAdapter.add(this)
        }

        ExpandableGroup(
            ExpandableHeaderItem(
                resources.getString(R.string.proteins).capitalize()
            ), false
        ).apply {
            //            excitingSection.addAll()
            add(Section(generateItemSupermarketListViews(2)))
            groupAdapter.add(this)
        }

        ExpandableGroup(
            ExpandableHeaderItem(
                resources.getString(R.string.carbohydrates).capitalize()
            ), false
        ).apply {
            add(Section(generateItemSupermarketListViews(3)))
            groupAdapter.add(this)
        }

        ExpandableGroup(
            ExpandableHeaderItem(
                resources.getString(R.string.healthyFats).capitalize()
            ), false
        ).apply {

            add(Section(generateItemSupermarketListViews(4)))
            groupAdapter.add(this)
        }

        ExpandableGroup(
            ExpandableHeaderItem(
                resources.getString(R.string.seedsAndDressings).capitalize()
            ), false
        ).apply {
            add(Section(generateItemSupermarketListViews(5)))
            groupAdapter.add(this)
        }

        ExpandableGroup(
            ExpandableHeaderItem(
                resources.getString(R.string.oilsAndNutButters).capitalize()
            ), false
        ).apply {
            add(Section(generateItemSupermarketListViews(6)))
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
                    val supermarketItem = SupermarketItem(itemA, quantity)
                    this.supermarketList[i].add(supermarketItem)
                }
            }
        }
    }

    private fun removeSpace(item: String): String {
        return item.replace("\\s".toRegex(), "")
    }

    private fun generateItemSupermarketListViews(i: Int): MutableList<ItemSupermarketListView> {

        val mutableList = mutableListOf<ItemSupermarketListView>()
        for (item in supermarketList[i]) {

            val titleFormated = if (item.quantity > 1) {
                item.name + " (" + item.quantity.toString() + ")"
            } else {
                item.name
            }.capitalize()
            mutableList.add(ItemSupermarketListView(titleFormated))
        }
        return mutableList
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.supermarket_list_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.shareSupermarketListMenu) {

        } else {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}