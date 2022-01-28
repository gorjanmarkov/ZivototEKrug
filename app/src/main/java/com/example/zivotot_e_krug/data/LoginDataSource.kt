package com.example.zivotot_e_krug.data

import android.content.ContentValues.TAG

import android.util.Log

import kotlinx.coroutines.*
import com.example.zivotot_e_krug.data.model.LoggedInUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.NonCancellable.join
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */

@InternalCoroutinesApi
class LoginDataSource {

    lateinit var type: String
    var eventListener: ValueEventListener = object :
        ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            type = dataSnapshot.child("users")
                .child(auth.currentUser?.let { it.uid }.toString())
                .child("Type").value.toString()


        }

        override fun onCancelled(databaseError: DatabaseError) {
            // Getting Post failed, log a message
            Log.w(
                TAG,
                "loadPost:onCancelled",
                databaseError.toException()
            )
        }
    }
    lateinit var user: LoggedInUser
    val auth: FirebaseAuth = Firebase.auth
    private var database: DatabaseReference = Firebase.database.reference
    fun login(username: String, password: String): Result<LoggedInUser> {

        try {

            auth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        database.addValueEventListener(eventListener)
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("LOGIN", "signInWithEmail:success")
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        // throw IllegalArgumentException("Invalid username or password")
                    }
                }

            user = LoggedInUser(
                auth.currentUser?.let { it.displayName }.toString(),
                auth.uid.toString(),
                type
            )

            return Result.Success(user)
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        auth.signOut()
    }
}