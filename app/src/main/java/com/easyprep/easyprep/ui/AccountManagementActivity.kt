package com.easyprep.easyprep.ui

import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v7.app.AppCompatActivity
import android.text.InputType
import android.view.inputmethod.InputMethodManager
import com.easyprep.easyprep.R
import com.easyprep.easyprep.ui.mainActivity.MainActivity
import kotlinx.android.synthetic.main.activity_account_management.*

class AccountManagementActivity : AppCompatActivity() {

    private var typeManagement = ""
    private lateinit var currentItem: TextInputEditText
    private lateinit var newItem: TextInputEditText
    private lateinit var newItemConfirmation: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_management)

        activity_account_management_id.setOnTouchListener { p0, p1 ->
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
            textInputEditText_current_item.clearFocus()
            textInputEditText_new_item.clearFocus()
            textInputEditText_new_item_confirmation.clearFocus()
            false
        }

        typeManagement = intent.getStringExtra(MainActivity.EXTRA_ACCOUNT_MANAGEMENT)

        supportActionBar!!.title = if(typeManagement == "EMAIL"){
            resources.getString(R.string.change_email)
        }else{
           resources.getString(R.string.change_password)
        }

        currentItem = findViewById(R.id.textInputEditText_current_item)
        newItem = findViewById(R.id.textInputEditText_new_item)
        newItemConfirmation = findViewById(R.id.textInputEditText_new_item_confirmation)

        if (typeManagement == "EMAIL") {
            setHints()
            setInputType()
        } else {

        }
    }

    private fun setHints() {
        this.textInputLayout_new_item.hint = resources.getString(R.string.new_email)
        this.textInputLayout_new_item_confirmation.hint =
                resources.getString(R.string.new_email_confirmation)
    }

    private fun setInputType() {
        this.newItem.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        this.newItemConfirmation.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
    }
}