package com.example.zivotot_e_krug.ui.users.adult

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zivotot_e_krug.data.model.Review
import com.example.zivotot_e_krug.data.model.TaskName
import com.example.zivotot_e_krug.data.model.TaskProperties
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class AdultViewModel : ViewModel() {
    private var database: DatabaseReference = Firebase.database.reference
    private var auth: FirebaseAuth = Firebase.auth
    val TAG = AdultViewModel::class.java.simpleName
    fun createTask(
        context: Context,
        user: FirebaseUser,
        title: String,
        description: String,
        repeatable: String,
        urgency: String,
        date: String,
        location: String,
        adult_name: String,
        adult_number: String,
        adult_rating: String,
    ): Boolean {
        when (title.isEmpty()) {
            true -> {
                Toast.makeText(
                    context, "Please enter a name for the task",
                    Toast.LENGTH_LONG
                ).show()
                return false
            }
            false -> when (description.isEmpty()) {
                true -> {
                    Toast.makeText(
                        context, "Please enter a description for the task",
                        Toast.LENGTH_LONG
                    ).show()
                    return false
                }
                false -> when (location == "Location") {
                    true -> {
                        Toast.makeText(
                            context, "Please pick a location for the task",
                            Toast.LENGTH_LONG
                        ).show()
                        return false
                    }
                    false -> {
                        val dataInfo = TaskProperties(
                            description,
                            repeatable,
                            urgency,
                            date,
                            location,
                            title, null.toString(), null.toString(), null.toString(),
                            adult_name,
                            adult_number,
                            auth.currentUser?.uid.toString(),
                            adult_rating
                        )
                        setTable(user, dataInfo, title)
                        Toast.makeText(context, "Task Created!", Toast.LENGTH_SHORT).show()
                        return true
                    }
                }

            }
        }
    }

    private fun setTable(user: FirebaseUser, dataInfo: TaskProperties, title: String) {
        database.child("users").child(user.uid)
            .child("Tasks")
            .child(title)
            .setValue(dataInfo)
        database.child("Tasks").child(title).setValue(dataInfo)
        database.addValueEventListener(postListener)
    }

    var location: MutableLiveData<String> = MutableLiveData()
    var _location: LiveData<String> = location

    val tasks: ArrayList<TaskProperties> = ArrayList()
    var response: MutableLiveData<TaskName> = MutableLiveData()
    var _response: LiveData<TaskName> = response
    var adult_name: MutableLiveData<String> = MutableLiveData()
    var _adult_name: LiveData<String> = adult_name
    var adult_number: MutableLiveData<String> = MutableLiveData()
    var _adult_number: LiveData<String> = adult_number
    var adult_rating: MutableLiveData<String> = MutableLiveData()
    var _adult_rating: LiveData<String> = adult_rating
    private val postListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {


            for (postSnapshot in dataSnapshot.child("users").child(auth.currentUser?.uid.toString())
                .child("Tasks").children) {
                val task = TaskProperties(
                    postSnapshot.child("description").value.toString(),
                    postSnapshot.child("repeatable").value.toString(),
                    postSnapshot.child("urgency").value.toString(),
                    postSnapshot.child("date").value.toString(),
                    postSnapshot.child("location").value.toString(),
                    postSnapshot.child("_title").value.toString(),
                    postSnapshot.child("volunteer_name").value.toString(),
                    postSnapshot.child("volunteer_number").value.toString(),
                    postSnapshot.child("volunteer_uid").value.toString(),
                    postSnapshot.child("adult_name").value.toString(),
                    postSnapshot.child("adult_number").value.toString(),
                    postSnapshot.child("adult_uid").value.toString(),
                    postSnapshot.child("adult_rating").value.toString(),
                    postSnapshot.child("active").value.toString(),
                )
                tasks.add(task)
            }
            val taskList = TaskName(tasks)
            response.value = taskList
        }

        override fun onCancelled(databaseError: DatabaseError) {
            // Getting Post failed, log a message
            Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
        }
    }
    val reviewList: ArrayList<Review> = ArrayList()
    var review: MutableLiveData<ArrayList<Review>> = MutableLiveData()
    var _review: LiveData<ArrayList<Review>> = review
    private val reviewListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {

            for (postSnapshot in dataSnapshot.child("users").child(auth.currentUser!!.uid)
                .child("Tasks").children) {
                if (postSnapshot.child("active").value.toString() == "FINISHED") {
                    val task = Review(
                        postSnapshot.child("_title").value.toString(),
                        dataSnapshot.child("users").child(
                            postSnapshot.child("volunteer_uid").value.toString()
                        ).child("FirstName").value.toString() + " " +
                                dataSnapshot.child("users").child(
                                    postSnapshot.child("volunteer_uid").value.toString()
                                ).child("LastName").value.toString(),
                        dataSnapshot.child("users").child(
                            postSnapshot.child("volunteer_uid").value.toString()
                        ).child("number").value.toString(),
                        postSnapshot.child("volunteer_uid").value.toString(),
                        dataSnapshot.child("users").child(
                            postSnapshot.child("volunteer_uid").value.toString()
                        ).child("rating").value.toString() ?: "0",
                        dataSnapshot.child("users").child(
                            postSnapshot.child("volunteer_uid").value.toString()
                        ).child("sum").value.toString() ?: "0",
                        dataSnapshot.child("users").child(
                            postSnapshot.child("volunteer_uid").value.toString()
                        ).child("number_of_raters").value.toString()?: "0"
                    )
                    reviewList.add(task)
                }
            }
            review.value = reviewList
        }

        override fun onCancelled(databaseError: DatabaseError) {
            // Getting Post failed, log a message
            Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
        }
    }
    private val locationListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            location.value = snapshot.child("Address").value.toString()
            adult_name.value = snapshot.child("users").child(auth.currentUser?.uid.toString())
                .child("FirstName").value.toString() +
                    " " + snapshot.child("users").child(auth.currentUser?.uid.toString())
                .child("LastName").value.toString()
            adult_number.value = snapshot.child("users").child(auth.currentUser?.uid.toString())
                .child("Number").value.toString()
            adult_rating.value = snapshot.child("users").child(auth.currentUser?.uid.toString())
                .child("Rating").value.toString()
        }

        override fun onCancelled(databaseError: DatabaseError) {
            // Getting Post failed, log a message
            Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
        }
    }

    fun addDataListener() {
        database.addValueEventListener(postListener)
    }

    fun addLocationListener() {
        database.addValueEventListener(locationListener)
    }

    fun addReviewListener() {
        database.addValueEventListener(reviewListener)
    }
}