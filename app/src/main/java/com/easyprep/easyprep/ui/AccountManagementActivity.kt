package com.easyprep.easyprep.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.easyprep.easyprep.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_account_management.*


class AccountManagementActivity : AppCompatActivity() {

    private var typeManagement = 0
    private var mAuth: FirebaseAuth? = null

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_management)

        activity_account_management_id.setOnTouchListener { p0, p1 ->
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
            editText_password_confirmation_retrieve.clearFocus()
            editText_password_retrieve.clearFocus()
            editText_email_retrieve.clearFocus()
            false
        }

        val password = findViewById<TextInputEditText>(R.id.editText_password_retrieve)
        val passwordConfirmation = findViewById<TextInputEditText>(R.id.editText_password_confirmation_retrieve)

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

        if (typeManagement == 0) {
            editText_password_retrieve_layout.visibility = View.GONE
            editText_password_confirmation_retrieve_layout.visibility = View.GONE
        }

        btnConfirm.setOnClickListener {
            retrieveAccount()
        }
    }

    private fun retrieveAccount() {
        val email = editText_email_retrieve.text.toString()
        if (email.isNotEmpty()) {
            this.mAuth!!.sendPasswordResetEmail(email).addOnCompleteListener { task ->
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
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
        return super.onOptionsItemSelected(item)
    }
}