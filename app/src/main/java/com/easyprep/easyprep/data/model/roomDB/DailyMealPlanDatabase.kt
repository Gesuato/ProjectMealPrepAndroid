package com.easyprep.easyprep.data.model.roomDB

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.easyprep.easyprep.data.model.DailyMealPlan

@Database(entities = [DailyMealPlan::class], version = 1, exportSchema = false)
abstract class DailyMealPlanDatabase : RoomDatabase() {
    abstract fun dailyMealPlanDAO(): DailyMealPlanDAO
}