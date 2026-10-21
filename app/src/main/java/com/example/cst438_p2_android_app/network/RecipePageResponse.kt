package com.example.cst438_p2_android_app.network

data class RecipePageResponse(
    val content: List<RecipeResponse>,
    val page: PageMetadata
)