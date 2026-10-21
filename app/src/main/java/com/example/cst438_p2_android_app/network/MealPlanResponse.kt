package com.example.cst438_p2_android_app.network

/**
 * this defines the android data structure
 * It makes it easier to read the API responeses
 * It basically defines the data format
 */
data class MealPlanResponse(
    val id: Long,
    val weekStart: String,
    val entries: List<MealPlanEntryResponse>
)

data class MealPlanEntryResponse(
    val id: Long,
    val mealPlanId: Long,
    val recipeId: Long,
    val plannedDate: String,
    val mealType: String
)