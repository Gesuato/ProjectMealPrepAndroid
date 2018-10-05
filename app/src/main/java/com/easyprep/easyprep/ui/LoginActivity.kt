package com.easyprep.easyprep.ui

import android.content.Intent
import android.content.Intent.EXTRA_USER
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.easyprep.easyprep.R
import com.easyprep.easyprep.data.model.DailyMealPlanDefaultListBuilder
import com.easyprep.easyprep.data.model.WeekMealPlan
import com.easyprep.easyprep.data.model.login.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null
    private var mAuthListener: FirebaseAuth.AuthStateListener? = null
    private var email = ""
    private var password = ""
    private val db = FirebaseFirestore.getInstance()
    private val weekRef = db.collection(DB_REF)
    private var weekMealPlan = WeekMealPlan()
    private lateinit var userSharePref : SharedPreferences

    companion object {
        const val DB_REF = "WeekMealPlan"
        const val EXTRA_WEEK_MEAL_PLAN = "EXTRA_WEEK"
        const val EXTRA_USER = "CURRENTUSER"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
         userSharePref = PreferenceManager.getDefaultSharedPreferences(this)


        mAuth = FirebaseAuth.getInstance()
        mAuthListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val userAuth = firebaseAuth.currentUser
            if (userAuth != null) {
                userSharePref.edit().putString(EXTRA_USER,userAuth.uid).apply()
                loadDataInFirestore(userAuth.uid)
                // User is signed in
                Log.d("LoginAuth", "onAuthStateChanged:signed_in:" + userAuth.uid)
            } else {
                editTextPassword.visibility = View.VISIBLE
                editTextEmail.visibility = View.VISIBLE
                btnLogin.visibility = View.VISIBLE
                userSharePref.edit().putString(EXTRA_USER,null).apply()
                Log.d("LoginAuth", "onAuthStateChanged:signed_out")
            }
        }
        editTextEmail.visibility = View.GONE
        editTextPassword.visibility = View.GONE
        btnLogin.visibility = View.GONE

        btnLogin.setOnClickListener {
            email = editTextEmail.text.toString()
            password = editTextPassword.text.toString()
            print(email)

            mAuth!!.signInWithEmailAndPassword("email@gmail.com", "123456")
                .addOnCompleteListener(
                    this
                ) { task ->

                    Log.d("LoginSucess", "signInWithEmail:onComplete:" + task.isSuccessful)

                    if (!task.isSuccessful) {
                        Log.w("LoginFailed", "signInWithEmail:failed", task.exception)
                        Toast.makeText(
                            this, "Failed",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }

    override fun onStart() {
        super.onStart()
        mAuth!!.addAuthStateListener(mAuthListener!!)
    }

    override fun onStop() {
        super.onStop()
        if (mAuthListener != null) {
            mAuth!!.removeAuthStateListener(mAuthListener!!)
        }
    }

    private fun loadDataInFirestore(userUID: String) {
        weekMealPlan.dailyMealPlanList = DailyMealPlanDefaultListBuilder().invoke()

        weekRef.document(userUID).get().addOnSuccessListener { documentSnapshot ->
            var saveData = false
            if (documentSnapshot.exists()) {
                weekMealPlan = documentSnapshot.toObject(WeekMealPlan::class.java)!!

            } else {
                saveData = true
            }

            if (saveData) {
                saveData(weekMealPlan, userUID)
            }

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(EXTRA_WEEK_MEAL_PLAN, weekMealPlan)
            startActivity(intent)
            finish()
        }.addOnFailureListener {

        }
    }

    private fun saveData(weekMealPlan: WeekMealPlan, userUID: String) {
        weekRef.document(userUID).set(weekMealPlan)
    }
}
