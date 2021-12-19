package com.example.zivotot_e_krug.ui.users.adult

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.zivotot_e_krug.R
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*


class AdultViewModel : ViewModel() {
    private var database: DatabaseReference = Firebase.database.reference
    val TITLE = "Title"
    val DESCRIPTION = "Description"
    val REPEATABLE = "Repeatable"
    val URGENCY = "Urgency"
    val DATE = "Date"
    val LOCATION = "Location"
    fun createTask(
        context: Context,
        user: FirebaseUser,
        title: String,
        description: String,
        repeatable: String,
        urgency: String,
        date: String,
        location: String
    ) {
        when (title.isEmpty()) {
            true -> Toast.makeText(
                context, "Please enter a name for the task",
                Toast.LENGTH_LONG
            ).show()
            false -> when (description.isEmpty()) {
                true -> Toast.makeText(
                    context, "Please enter a description for the task",
                    Toast.LENGTH_LONG
                ).show()
                false -> when (!date.equals("DD/MM/YYYY")) {
                    true -> Toast.makeText(
                        context, "Please pick a date for the task",
                        Toast.LENGTH_LONG
                    ).show()
                    false -> when (!location.equals("Location")) {
                        true -> Toast.makeText(
                            context, "Please pick a location for the task",
                            Toast.LENGTH_LONG
                        ).show()
                        false -> {
                            setTable(user, TITLE, title)
                            setTable(user, DESCRIPTION, description)
                            setTable(user, REPEATABLE, repeatable)
                            setTable(user, URGENCY, urgency)
                            setTable(user, DATE, date)
                            setTable(user, LOCATION, location)
                        }
                    }
                }
            }
        }
    }

    private fun setTable(user: FirebaseUser, key: String, value: String) {
        database.child(user.uid).child(key).setValue(value)
    }
}