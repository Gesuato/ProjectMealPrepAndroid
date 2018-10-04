package com.easyprep.easyprep.data.model

import java.io.Serializable

data class WeekMealPlan(var dailyMealPlanList: List<DailyMealPlan> = emptyList()) : Serializable
