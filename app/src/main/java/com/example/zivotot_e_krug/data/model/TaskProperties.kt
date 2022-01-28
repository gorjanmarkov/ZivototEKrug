package com.example.zivotot_e_krug.data.model

data class TaskProperties (
        val description : String,
        val repeatable: String,
        val urgency: String,
        var date : String,
        val location : String,
        val _title : String,
        var volunteer_name : String = null.toString(),
        var volunteer_number: String = null.toString(),
        var volunteer_uid : String = null.toString(),
        val adult_name : String = null.toString(),
        val adult_number: String = null.toString(),
        val adult_uid : String = null.toString(),
        var adult_rating : String,
        var active : String = "PENDING"
        )


