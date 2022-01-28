package com.example.zivotot_e_krug.ui.users.adult.adapters


import android.content.ContentValues
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.zivotot_e_krug.R
import com.example.zivotot_e_krug.data.model.Review
import com.example.zivotot_e_krug.data.model.TaskProperties
import com.example.zivotot_e_krug.ui.login.afterTextChanged
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*


class TaskBinding {

    companion object {

        @BindingAdapter("setVolunteer")
        @JvmStatic
        fun setVolunteer(view: View, task: TaskProperties) {
            val database: DatabaseReference = Firebase.database.reference
            val auth: FirebaseAuth = FirebaseAuth.getInstance()
            lateinit var name: String
            lateinit var number: String
            lateinit var uid: String

            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    name = dataSnapshot.child("users")
                        .child(auth.currentUser?.let { it.uid }.toString())
                        .child("FirstName").value.toString() + " " +
                            dataSnapshot.child("users")
                                .child(auth.currentUser?.let { it.uid }.toString())
                                .child("LastName").value.toString()
                    number = dataSnapshot.child("users")
                        .child(auth.currentUser?.let { it.uid }.toString())
                        .child("Number").value.toString()
                    uid = auth.currentUser?.let { it.uid }.toString()
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w(ContentValues.TAG, "loadPost:onCancelled", error.toException())
                }
            })
            if (task.active == "PENDING") {
                view.setOnClickListener {
                    task.volunteer_name = name
                    task.volunteer_number = number
                    task.volunteer_uid = uid
                    task.active = "ACTIVE"
                    database.child("Tasks").child(task._title).setValue(task)
                    database.child("users").child(task.adult_uid).child("Tasks")
                        .child(task._title)
                        .setValue(task)
                    database.child("users").child(task.volunteer_uid).child("Tasks")
                        .child(task._title)
                        .setValue(task)
                }
            }

        }

//        @RequiresApi(Build.VERSION_CODES.O)
//        @BindingAdapter("setRepetitive")
//        @JvmStatic
//        fun setRepetitive(view: View, task: TaskProperties) {
//            val database: DatabaseReference = Firebase.database.reference
//            val c: Date = Calendar.getInstance().time
//            val df = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
//            val date : Date = df.parse(task.date)
//            if(date.after(c)){
//                task.active="FINISHED"
//                if(task.repeatable=="true"){
//                    val pom = Calendar.getInstance()
//                    pom.add(Calendar.DATE,7)
//                    val newDate = df.format(pom.time)
//                    task.date = newDate.toString()
//                }
//                database.child("Tasks").child(task._title).setValue(task)
//                database.child("users").child(task.adult_uid).child("Tasks")
//                    .child(task._title)
//                    .setValue(task)
//                database.child("users").child(task.volunteer_uid).child("Tasks")
//                    .child(task._title)
//                    .setValue(task)
//            }
//
//        }

        @BindingAdapter("setRating")
        @JvmStatic
        fun setRating(editText: EditText, review: Review) {
            editText.afterTextChanged {
                val database: DatabaseReference = Firebase.database.reference
                var numberRatings = review.number_of_raters
                var sum = review.sum
                if (numberRatings == "null") {
                    numberRatings = "1"
                }
                else{
                    numberRatings = (numberRatings.toInt() + 1).toString()
                }
                if (sum == "null") {
                    sum = "0"
                }
                val newRating = it.toInt()
                if (newRating > 5 || newRating < 1) {
                    editText.error = "Rating is between 1 and 5"
                } else {
                    sum = (sum.toInt() + newRating).toString()
                    val rating = sum.toInt() / numberRatings.toInt()
                    database.child("users").child(review.uid).child("rating")
                        .setValue(rating.toString())
                    database.child("users").child(review.uid).child("sum")
                        .setValue(sum)
                    database.child("users").child(review.uid).child("number_of_raters")
                        .setValue(numberRatings)
                    editText.isEnabled = false
                }


            }
        }

        @BindingAdapter("setRatingAdult")
        @JvmStatic
        fun setRatingAdult(editText: EditText, review: Review) {
            editText.afterTextChanged {
                val database: DatabaseReference = Firebase.database.reference
                var numberRatings = review.number_of_raters
                var sum = review.sum
                if (numberRatings == "null") {
                    numberRatings = "1"
                }else{
                    numberRatings = (numberRatings.toInt() + 1).toString()
                }
                if (sum == "null") {
                    sum = "0"
                }
                val newRating = it.toInt()
                if (newRating > 5 || newRating < 1) {
                    editText.error = "Rating is between 1 and 5"
                } else {
                    sum = (sum.toInt() + newRating).toString()
                    val rating = sum.toInt() / numberRatings.toInt()
                    database.child("users").child(review.uid).child("rating")
                        .setValue(rating.toString())
                    database.child("users").child(review.uid).child("sum")
                        .setValue(sum)
                    database.child("users").child(review.uid).child("number_of_raters")
                        .setValue(numberRatings)
                    editText.isEnabled = false
                }


            }
        }

        @BindingAdapter("setActiveColor")
        @JvmStatic
        fun setActiveColor(imageView: ImageView, active: String) {
            if (active == "ACTIVE") {
                imageView.setColorFilter(
                    ContextCompat.getColor(
                        imageView.context,
                        R.color.Yellow
                    )
                )

            } else if (active == "PENDING") {
                imageView.setColorFilter(
                    ContextCompat.getColor(
                        imageView.context,
                        R.color.Red
                    )
                )
            } else {
                imageView.setColorFilter(
                    ContextCompat.getColor(
                        imageView.context,
                        R.color.chip_text_color
                    )
                )
            }
        }
    }
}