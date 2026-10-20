package com.example.cst438_p2_android_app.network

data class UserResponse(
    val id: Long,
    val email: String,
    val displayName: String?,
    val role: String
)