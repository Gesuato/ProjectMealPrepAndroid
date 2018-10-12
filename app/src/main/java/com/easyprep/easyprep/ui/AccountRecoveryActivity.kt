package com.easyprep.easyprep.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import com.easyprep.easyprep.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_account_recovery.*


class AccountRecoveryActivity : AppCompatActivity() {

    private var typeManagement = 0
    private var mAuth: FirebaseAuth? = null
    private lateinit var password: TextInputEditText
    private lateinit var passwordConfirmation: TextInputEditText
    private lateinit var email: TextInputEditText

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_recovery)

        activity_account_recovery_id.setOnTouchListener { p0, p1 ->
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
            textInputEditText_email_retrieve.clearFocus()
            false
        }

        email = findViewById(R.id.textInputEditText_email_retrieve)

        this.mAuth = FirebaseAuth.getInstance()
        this.typeManagement = intent.getIntExtra("TYPEMANEGEMENT", 0)

        supportActionBar!!.run {
            setDisplayHomeAsUpEnabled(true)
            if (typeManagement == 0) {
                title = resources.getString(R.string.retrieve_account)
            } else {
                title = resources.getString(R.string.change_password)
            }
        }

        btnConfirm.setOnClickListener {
            retrieveAccount()
        }
    }

    private fun retrieveAccount() {
        if (email.text.toString().isNotEmpty()) {
            this.mAuth!!.sendPasswordResetEmail(email.text.toString())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        textView_message_retrieve.text =
                                resources.getString(R.string.message_change_email)
                    } else {
                        textView_message_retrieve.text =
                                resources.getString(R.string.error_message_change_email)
                    }
                }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }
}