package com.example.cst438_p2_android_app.network

data class RecipeCreate(
    val name: String,
    val ingredientIds: Set<Long>,
    val measurements: String?,
    val instructions: String?
)