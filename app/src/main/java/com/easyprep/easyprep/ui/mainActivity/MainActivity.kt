package com.easyprep.easyprep.ui.mainActivity

import android.app.Dialog
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
import com.easyprep.easyprep.ui.AccountManagementActivity
import com.easyprep.easyprep.ui.LoginActivity
import com.easyprep.easyprep.ui.adapters.ViewPagerAdapter
import com.easyprep.easyprep.ui.supermarketActivity.SupermarketListActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_popup.view.*
import kotlinx.android.synthetic.main.popup_profile.*

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
    private lateinit var popupProfile: Dialog

    companion object {
        const val EXTRA_SUPERMARKETLIST = "SUPERMARKETLIST"
        const val EXTRA_ACCOUNT_MANAGEMENT = "EXTRA_MANAGEMENT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.userSharePref = PreferenceManager.getDefaultSharedPreferences(this)
        this.user.userId = this.userSharePref.getString(LoginActivity.EXTRA_USER, "")
        mAuth = FirebaseAuth.getInstance()
        popupProfile = Dialog(this)

        if (intent.getSerializableExtra(LoginActivity.EXTRA_WEEK_MEAL_PLAN) != null) {
            this.weekMealPlan =
                    intent.getSerializableExtra(LoginActivity.EXTRA_WEEK_MEAL_PLAN) as WeekMealPlan
            isLogged = true
        } else {
            this.weekMealPlan = WeekMealPlan(DailyMealPlanDefaultListBuilder().invoke())
        }

        this.viewPager = findViewById(R.id.viewPagerId)

        if (this.weekMealPlan.dailyMealPlanList.isNotEmpty()) {
            this.viewPagerAdapter = ViewPagerAdapter(
                supportFragmentManager,
                resources.getStringArray(R.array.pagerDayTitles),
                this.weekMealPlan
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
            }
            R.id.introductionMenu -> {

            }
            R.id.contactMenu -> {

            }
            R.id.singOutMenu -> {
                val intentLogin = Intent(this, LoginActivity::class.java)
                singOut()
                startActivity(intentLogin)
                finish()
            }
            R.id.profileMenu -> {
                showPopupProfile()

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

    private fun getCurrentEmail(): String {
        return mAuth!!.currentUser!!.email.toString()
    }

    private fun showPopupProfile() {
        val email = getCurrentEmail()
        val intentAccountManagement = Intent(this, AccountManagementActivity::class.java)

        this.popupProfile.setContentView(R.layout.popup_profile)
        this.popupProfile.item_popup_email.setTitleItemPopup(email)
        this.popupProfile.item_popup_email.setBackgroundImage(R.drawable.icon_edit)
        this.popupProfile.item_popup_email.imageButtonPopup.setOnClickListener {
            intentAccountManagement.putExtra(EXTRA_ACCOUNT_MANAGEMENT, "EMAIL")
            startActivity(intentAccountManagement)
        }

        this.popupProfile.item_popup_password.setTitleItemPopup("***********")
        this.popupProfile.item_popup_password.setBackgroundImage(R.drawable.icon_edit)
        this.popupProfile.item_popup_password.imageButtonPopup.setOnClickListener {
            intentAccountManagement.putExtra(EXTRA_ACCOUNT_MANAGEMENT, "PASSWORD")
            startActivity(intentAccountManagement)
        }

        this.popupProfile.btnCancelPopupProfile.setOnClickListener {
            this.popupProfile.dismiss()
        }

        this.popupProfile.show()
    }

    private fun singOut() {
        mAuth!!.signOut()
    }
}