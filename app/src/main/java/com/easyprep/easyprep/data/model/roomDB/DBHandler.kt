package com.easyprep.easyprep.data.model.roomDB

import android.arch.persistence.room.Room
import android.content.Context
import com.easyprep.easyprep.data.model.DailyMealPlan

data class DBHandler(var contex: Context) {

    private lateinit var dailyMealPlanDatabase: DailyMealPlanDatabase

    fun fetchData(): List<DailyMealPlan> {
        dailyMealPlanDatabase =
                Room.databaseBuilder(contex, DailyMealPlanDatabase::class.java, "dailyMealPlan.DB")
                    .allowMainThreadQueries().build()
        return dailyMealPlanDatabase.dailyMealPlanDAO().getAllDailyMealPlans()
    }

//    fun saveData(dailyMealPlanList: List<DailyMealPlan>) {
//        val dailyMeaPlanDB = DailyMealPlanDefaultListBuilder()
//
//        dailyMealPlanDatabase =
//                Room.databaseBuilder(contex, DailyMealPlanDatabase::class.java, "dailyMealPlan.DB")
//                    .allowMainThreadQueries().build()
//        dailyMealPlanDatabase.dailyMealPlanDAO().insertAll(dailyMeaPlanDB)
//    }
}