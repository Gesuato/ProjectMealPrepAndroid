package com.easyprep.easyprep.data.model.roomDB

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import com.easyprep.easyprep.data.model.DailyMealPlan


@Dao
interface DailyMealPlanDAO {

    @Query("SELECT * FROM dailymealplan")
    fun getAllDailyMealPlans(): List<DailyMealPlan>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(dailyMealPlanModelDB: DailyMealPlan)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(dailyMealPlanModelDBList: List<DailyMealPlan>)

    @Update
    fun update(dailyMealPlanModelDB: DailyMealPlan)

    @Delete
    fun delete(dailyMealPlanModelDB: DailyMealPlan)
}