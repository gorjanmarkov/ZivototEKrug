package com.example.zivotot_e_krug.data.model

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class LoggedInUser(
    val displayName: String,
    val userId: String,
    val type: String
)