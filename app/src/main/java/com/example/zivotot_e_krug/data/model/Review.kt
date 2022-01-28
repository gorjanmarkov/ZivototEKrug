package com.example.zivotot_e_krug.data.model

data class Review(
    val taskName : String,
    val name : String,
    val number : String,
    val uid : String,
    val rating : String = "0",
    val sum : String = "0",
    val number_of_raters : String = "0"
)
