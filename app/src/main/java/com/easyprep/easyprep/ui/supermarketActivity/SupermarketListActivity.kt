package com.easyprep.easyprep.ui.supermarketActivity

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
import com.easyprep.easyprep.ui.mainActivity.MainActivity
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_supermarket_list.*


class SupermarketListActivity : AppCompatActivity() {

    private lateinit var weekMealPlan: WeekMealPlan
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
    private lateinit var nutrimentTitles: Array<String>

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_supermarket_list)
        supportActionBar!!.run {
            setDefaultDisplayHomeAsUpEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            title = resources.getString(R.string.supermarketList)
        }

        if (!::weekMealPlan.isInitialized) {
            weekMealPlan =
                    intent.getSerializableExtra(MainActivity.EXTRA_SUPERMARKETLIST) as WeekMealPlan
        }
        if (!::nutrimentTitles.isInitialized) {
            this.nutrimentTitles = resources.getStringArray(R.array.nutrimentTitles)
        }

        removeDuplicateItemsInSupermarketList()

        val groupAdapter = GroupAdapter<ViewHolder>()

        recycleView_Supermarket.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = groupAdapter
        }

        (0..6).forEach { index ->
            val isExpandable = when (index) {
                0 -> true
                else -> {
                    false
                }
            }
            ExpandableGroup(
                ExpandableHeaderItem(
                    nutrimentTitles[index]
                ), isExpandable
            ).apply {
                add(Section(generateItemSupermarketListViews(index)))
                groupAdapter.add(this)
            }
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

        for (dailyMealPlan in weekMealPlan.dailyMealPlanList) {
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
            mutableList.add(
                ItemSupermarketListView(
                    titleFormated
                )
            )
        }
        return mutableList
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.supermarket_list_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.shareSupermarketListMenu) {
            shareSupermarketList()
        } else {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun shareSupermarketList() {
        val intent = Intent(Intent.ACTION_SEND)
        val subject =
            resources.getString(R.string.app_name) + " " + resources.getString(R.string.supermarketList)
        val message = formatedSupermarketListForEmail()

        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(Intent.EXTRA_TEXT, message)
        intent.type = "message/rfc822"
        startActivity(
            Intent.createChooser(
                intent,
                resources.getString(R.string.choose_email_client)
            )
        )
    }

    private fun formatedSupermarketListForEmail(): String {
        var messageSupermarketList = ""
        val titles = resources.getStringArray(R.array.nutrimentTitles)
        val colorTitles = arrayOf(R.color.vegetableColor)

        for ((index, supermarketItems) in supermarketList.withIndex()) {
            val title = titles[index].toUpperCase()
            if (index != 0) {
                messageSupermarketList = messageSupermarketList + "\n" +
                        title + "\n"
            } else {
                messageSupermarketList = messageSupermarketList +
                        title + "\n"
            }
            for ((i, item) in supermarketItems.withIndex()) {

                if (i != supermarketItems.size - 1) {
                    messageSupermarketList = messageSupermarketList + item.name + " (" +
                            item.quantity +
                            "),\n"
                } else {
                    messageSupermarketList = messageSupermarketList + item.name + " (" +
                            item.quantity +
                            ")"
                    messageSupermarketList += "\n"
                }
            }
        }

        return messageSupermarketList
    }
}