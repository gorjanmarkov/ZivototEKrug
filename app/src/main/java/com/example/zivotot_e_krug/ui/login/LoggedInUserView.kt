package com.example.zivotot_e_krug.ui.login

/**
 * User details post authentication that is exposed to the UI
 */
data class LoggedInUserView(
    val displayName: String,
    val uid : String,
    val type: String,
    //... other data fields that may be accessible to the UI
)