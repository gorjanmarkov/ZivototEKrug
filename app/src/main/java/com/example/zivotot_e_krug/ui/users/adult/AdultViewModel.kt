package com.example.zivotot_e_krug.ui.users.adult

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zivotot_e_krug.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import java.util.*


class AdultViewModel : ViewModel() {
    private var database: DatabaseReference = Firebase.database.reference
    private var auth : FirebaseAuth = Firebase.auth
    val TITLE = "Title"
    val DESCRIPTION = "Description"
    val REPEATABLE = "Repeatable"
    val URGENCY = "Urgency"
    val DATE = "Date"
    val LOCATION = "Location"
    val TAG = AdultViewModel::class.java.simpleName
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
                    false -> when (location != LOCATION) {
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
                        }
                    }

            }
        }
    }

    private fun setTable(user: FirebaseUser, key: String, value: String) {
        database.child(user.uid).child(key).setValue(value)
        database.addValueEventListener(postListener)
    }
    var location : MutableLiveData<String> = MutableLiveData<String>()
    var _location : LiveData<String> = location

    val postListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            // Get Post object and use the values to update the UI
            val post = dataSnapshot.child("users").child("2131230968").child(auth.currentUser!!.uid).child("Location").value
            location.value = post.toString()
        }

        override fun onCancelled(databaseError: DatabaseError) {
            // Getting Post failed, log a message
            Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
        }
    }
    fun addListener(){
        database.addValueEventListener(postListener)
    }
}