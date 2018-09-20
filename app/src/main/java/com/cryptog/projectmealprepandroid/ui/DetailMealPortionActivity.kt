package com.cryptog.projectmealprepandroid.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.cryptog.projectmealprepandroid.R
import com.cryptog.projectmealprepandroid.ui.adapters.MealDetailAdapter
import kotlinx.android.synthetic.main.activity_detail_meal_portion.*

class DetailMealPortionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_meal_portion)

        val layoutManager = LinearLayoutManager(this)
        recycleViewDetailId.layoutManager = layoutManager
        recycleViewDetailId.adapter = MealDetailAdapter()

        btnCancel.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                finish()
            }
        })
    }
}
