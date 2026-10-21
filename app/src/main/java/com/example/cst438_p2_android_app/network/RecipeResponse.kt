package com.example.cst438_p2_android_app.network

data class RecipeResponse(
    val id: Long,
    val name: String,
    val createdAt: String,
    val ingredients: List<IngredientResponse>,
    val measurements: String?,
    val instructions: String?
)