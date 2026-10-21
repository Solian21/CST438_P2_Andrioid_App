package com.example.cst438_p2_android_app.network

data class IngredientPageResponse(
    val content: List<IngredientResponse>,
    val page: PageMetadata
)