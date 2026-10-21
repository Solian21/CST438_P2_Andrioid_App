package com.example.cst438_p2_android_app.network

data class UserPageResponse(
    val content: List<UserResponse>,
    val page: PageMetadata
)