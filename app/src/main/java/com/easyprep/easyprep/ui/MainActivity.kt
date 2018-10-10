package com.easyprep.easyprep.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.easyprep.easyprep.R
import com.easyprep.easyprep.data.model.DailyMealPlanDefaultListBuilder
import com.easyprep.easyprep.data.model.WeekMealPlan
import com.easyprep.easyprep.data.model.login.User
import com.easyprep.easyprep.ui.adapters.ViewPagerAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var viewPager: ViewPager
    private var weekMealPlan = WeekMealPlan()
    private val db = FirebaseFirestore.getInstance()
    private val weekRef = db.collection(LoginActivity.DB_REF)
    private var user = User("")
    private var isLogged = false
    private lateinit var userSharePref: SharedPreferences
    private var mAuth: FirebaseAuth? = null

    companion object {
        const val EXTRA_SUPERMARKETLIST = "SUPERMARKETLIST"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userSharePref = PreferenceManager.getDefaultSharedPreferences(this)
        user.userId = userSharePref.getString(LoginActivity.EXTRA_USER, "")
        mAuth = FirebaseAuth.getInstance()

        if (intent.getSerializableExtra(LoginActivity.EXTRA_WEEK_MEAL_PLAN) != null) {
            weekMealPlan =
                    intent.getSerializableExtra(LoginActivity.EXTRA_WEEK_MEAL_PLAN) as WeekMealPlan
            isLogged = true
        } else if (intent.getSerializableExtra(EXTRA_SUPERMARKETLIST) != null) {
            weekMealPlan = intent.getSerializableExtra(EXTRA_SUPERMARKETLIST) as WeekMealPlan
        } else {
            weekMealPlan = WeekMealPlan(DailyMealPlanDefaultListBuilder().invoke())
        }

        this.viewPager = findViewById(R.id.viewPagerId)

        if (weekMealPlan.dailyMealPlanList.isNotEmpty()) {
            this.viewPagerAdapter = ViewPagerAdapter(
                supportFragmentManager,
                resources.getStringArray(R.array.pagerDayTitles),
                weekMealPlan
            )
            this.viewPager.adapter = this.viewPagerAdapter
            tabLayoutId.setupWithViewPager(viewPager)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val intent: Intent

        when (item!!.itemId) {
            R.id.supermarketListMenu -> {
                intent = Intent(this, SupermarketListActivity::class.java)
                intent.putExtra(EXTRA_SUPERMARKETLIST, weekMealPlan)
                startActivity(intent)
                finish()
            }
            R.id.introductionMenu -> {

            }
            R.id.contactMenu -> {

            }
            R.id.singOutMenu -> {
                val intent = Intent(this,LoginActivity::class.java)
                singOut()
                startActivity(intent)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun getWeek(): WeekMealPlan = weekMealPlan

    fun saveData(weekMealPlan: WeekMealPlan) {
        if (user.userId != "") {
            weekRef.document(user.userId).set(weekMealPlan)
        }
    }

    private fun singOut() {
        mAuth!!.signOut()
    }
}
