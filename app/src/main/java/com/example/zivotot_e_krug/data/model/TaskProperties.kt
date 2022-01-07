package com.example.zivotot_e_krug.data.model

data class TaskProperties (
        val description : String,
        val repeatable: String,
        val urgency: String,
        val date : String,
        val location : String,
        val _title : String,
        val volunteer_name : String = null.toString(),
        val volunteer_number: String = null.toString(),
        val active : String = "NOT_ACTIVE"
        )


