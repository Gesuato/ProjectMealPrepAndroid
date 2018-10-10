package com.easyprep.easyprep.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.TextInputEditText
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.easyprep.easyprep.R
import com.easyprep.easyprep.data.model.DailyMealPlanDefaultListBuilder
import com.easyprep.easyprep.data.model.WeekMealPlan
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null
    private var mAuthListener: FirebaseAuth.AuthStateListener? = null
    private val db = FirebaseFirestore.getInstance()
    private val weekRef = db.collection(DB_REF)
    private var weekMealPlan = WeekMealPlan()
    private lateinit var userSharePref: SharedPreferences
    private var isSingUp = false
    private lateinit var textInputEditTextPassword: TextInputEditText
    private lateinit var textInputEditTextPasswordConfirmation: TextInputEditText
    private lateinit var textInputEditextEmail: TextInputEditText

    companion object {
        const val DB_REF = "WeekMealPlan"
        const val EXTRA_WEEK_MEAL_PLAN = "EXTRA_WEEK"
        const val EXTRA_USER = "CURRENTUSER"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        textInputEditTextPassword = findViewById(R.id.editTextPasswordId)
        textInputEditTextPasswordConfirmation = findViewById(R.id.editTextConfirmationPasswordId)
        textInputEditextEmail = findViewById(R.id.textInputEditTextEmail)

        activity_login_id.setOnTouchListener { p0, p1 ->
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
            textInputEditTextPasswordConfirmation.clearFocus()
            textInputEditTextPassword.clearFocus()
            textInputEditextEmail.clearFocus()
            false
        }

        this.userSharePref = PreferenceManager.getDefaultSharedPreferences(this)
        supportActionBar!!.title = resources.getString(R.string.sing_in)
        this.mAuth = FirebaseAuth.getInstance()

        userAuthentication()

        btnLogin.setOnClickListener {
            val email = textInputEditextEmail.text.toString()
            val password = textInputEditTextPassword.text.toString()
            val passwordConfirmation = textInputEditTextPasswordConfirmation.text.toString()

            if (this.isSingUp) {
                singUp(email, password, passwordConfirmation)
            } else {
                singIn(email, password)
            }
        }
        btnSingUpOrSingIn.setOnClickListener {
            singUpOrSingIn()
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
        btnLogin.visibility = View.GONE
        btnSingUpOrSingIn.visibility = View.GONE
        textInputLayout_email.visibility = View.GONE
        textInputLayout_password_confirmation.visibility = View.GONE
        textInputLayout_password.visibility = View.GONE
        space.visibility = View.GONE
        progressBar.visibility = View.VISIBLE

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

    private fun singUp(email: String, password: String, passwordConfirmation: String) {
        if (!email.isBlank() && !password.isBlank() && !passwordConfirmation.isBlank()) {
            if (password == passwordConfirmation) {
                mAuth!!.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(
                        this
                    ) { task ->
                        if (!task.isSuccessful) {
                            Toast.makeText(
                                this,
                                task.exception!!.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            } else {
                Toast.makeText(
                    this,
                    resources.getString(R.string.warning_incorrect_password_confirmation),
                    Toast.LENGTH_SHORT
                ).show()
            }

        } else {
            Toast.makeText(
                this,
                resources.getString(R.string.warning_empty_fields),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun singIn(email: String, password: String) {


        if (!email.isBlank() && !password.isBlank()) {
            mAuth!!.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                    this
                ) { task ->

                    Log.d("LoginSucess", "signInWithEmail:onComplete:" + task.isSuccessful)

                    if (!task.isSuccessful) {
                        Log.w("LoginFailed", "signInWithEmail:failed", task.exception)
                        Toast.makeText(
                            this, task.exception!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        } else {
            Toast.makeText(
                this,
                resources.getString(R.string.warning_empty_fields),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun userAuthentication() {

        mAuthListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val userAuth = firebaseAuth.currentUser
            if (userAuth != null) {
                userSharePref.edit().putString(EXTRA_USER, userAuth.uid).apply()
                loadDataInFirestore(userAuth.uid)
            } else {
                userSharePref.edit().putString(EXTRA_USER, null).apply()
            }
        }
    }

    private fun singUpOrSingIn() {
        if (this.isSingUp) {
            this.isSingUp = false
            supportActionBar!!.title = resources.getString(R.string.sing_in)
            textInputLayout_password_confirmation.visibility = View.GONE
            btnLogin.setText(R.string.sing_in)
            btnSingUpOrSingIn.setText(R.string.sing_up_button)
        } else {
            this.isSingUp = true
            supportActionBar!!.title = resources.getString(R.string.sing_up)
            textInputLayout_password_confirmation.visibility = View.VISIBLE
            btnLogin.setText(R.string.sing_up)
            btnSingUpOrSingIn.setText(R.string.sing_in_button)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.login_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.retrieve_account_menu) {
            val intent = Intent(this, AccountManagementActivity::class.java)
            startActivity(intent)
            finish()
        } else {

        }
        return super.onOptionsItemSelected(item)
    }
}