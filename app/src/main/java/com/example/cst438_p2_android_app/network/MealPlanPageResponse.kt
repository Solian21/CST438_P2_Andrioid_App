package com.example.cst438_p2_android_app.network

/**
 * This give our Android app the structure of our response
 */

data class MealPlanPageResponse(
    val content: List<MealPlanResponse>,
    val page: PageMetadata
)

data class PageMetadata(
    val size: Int,
    val number: Int,
    val totalElements: Long,
    val totalPages: Int
)