package com.example.zivotot_e_krug.ui.users.volunteer

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zivotot_e_krug.data.model.Review
import com.example.zivotot_e_krug.data.model.TaskName
import com.example.zivotot_e_krug.data.model.TaskProperties
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class VolunteerViewModel : ViewModel() {
    private var database: DatabaseReference = Firebase.database.reference
    private var auth: FirebaseAuth = Firebase.auth
    val tasks: ArrayList<TaskProperties> = ArrayList()
    var response: MutableLiveData<TaskName> = MutableLiveData()
    var _response: LiveData<TaskName> = response
    private val postListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {

            for (postSnapshot in dataSnapshot.child("Tasks").children) {
                if (postSnapshot.child("active").value.toString() == "PENDING") {
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
                        dataSnapshot.child("users")
                            .child(postSnapshot.child("adult_uid").value.toString())
                            .child("rating").value.toString(),
                        postSnapshot.child("active").value.toString(),
                    )
                    tasks.add(task)
                }

            }
            val taskList = TaskName(tasks)
            response.value = taskList
        }

        override fun onCancelled(error: DatabaseError) {
            Log.w(TAG, "loadPost:onCancelled", error.toException())
        }
    }

    fun addDataListener() {
        database.addValueEventListener(postListener)
    }

    val volunteerTasks: ArrayList<TaskProperties> = ArrayList()
    var vresponse: MutableLiveData<TaskName> = MutableLiveData()
    var _vresponse: LiveData<TaskName> = vresponse
    private val vpostListener = object : ValueEventListener {
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
                    dataSnapshot.child("users")
                        .child(postSnapshot.child("adult_uid").value.toString())
                        .child("rating").value.toString(),
                    postSnapshot.child("active").value.toString(),
                )
                volunteerTasks.add(task)
            }
            val taskList = TaskName(volunteerTasks)
            vresponse.value = taskList
        }

        override fun onCancelled(error: DatabaseError) {
            Log.w(TAG, "loadPost:onCancelled", error.toException())
        }
    }

    fun addVDataListener() {
        database.addValueEventListener(vpostListener)
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
                            postSnapshot.child("adult_uid").value.toString()
                        ).child("FirstName").value.toString() + " " +
                                dataSnapshot.child("users").child(
                                    postSnapshot.child("adult_uid").value.toString()
                                ).child("LastName").value.toString(),
                        dataSnapshot.child("users").child(
                            postSnapshot.child("adult_uid").value.toString()
                        ).child("Number").value.toString(),
                        postSnapshot.child("adult_uid").value.toString(),
                        dataSnapshot.child("users").child(
                            postSnapshot.child("adult_uid").value.toString()
                        ).child("rating").value.toString() ?: "0",
                        dataSnapshot.child("users").child(
                            postSnapshot.child("adult_uid").value.toString()
                        ).child("sum").value.toString() ?: "0",
                        dataSnapshot.child("users").child(
                            postSnapshot.child("adult_uid").value.toString()
                        ).child("number_of_raters").value.toString() ?: "0"
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

    fun addReviewListener() {
        database.addValueEventListener(reviewListener)
    }
}