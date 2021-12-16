package com.example.zivotot_e_krug.ui.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.Toast
import com.example.zivotot_e_krug.R
import com.example.zivotot_e_krug.databinding.ActivityLoginBinding
import com.example.zivotot_e_krug.databinding.ActivityRegisterBinding
import com.google.android.material.chip.ChipGroup
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    private val TAG by lazy { RegisterActivity::class.java.simpleName }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        val username = binding.email
        val password = binding.password
        val repeatPassword = binding.repeatPassword
        val fistName = binding.fistName
        val lastname = binding.lastName
        val number = binding.number
        val roleGroup = binding.chipGroup
        var role : Int = 0
        val button = binding.login

        button.setOnClickListener {
            if (username.text.isEmpty() || !username.text.contains("@")) {
                Toast.makeText(this, "Wrong input for E-mail", Toast.LENGTH_LONG).show()
            } else if (password.text.isEmpty() || password.text.length < 5) {
                Toast.makeText(this, "Too short", Toast.LENGTH_LONG).show()
            } else if (repeatPassword.text.toString() != password.text.toString()) {
                Toast.makeText(this, "Passwords don't match", Toast.LENGTH_LONG).show()
            } else if (fistName.text.isEmpty()) {
                Toast.makeText(this, "Enter your First name", Toast.LENGTH_LONG).show()
            } else if (lastname.text.isEmpty()) {
                Toast.makeText(this, "Enter your Last name", Toast.LENGTH_LONG).show()
            } else if (number.text.isEmpty() || !number.text.startsWith("07") ||
                !number.text.startsWith("+389")) {
                Toast.makeText(this, "Wrong input for your number", Toast.LENGTH_LONG).show()
            } else {
                auth.createUserWithEmailAndPassword(
                    username.text.toString(),
                    password.text.toString()
                )
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success")
                            role = roleGroup.checkedChipId
                            val user = auth.currentUser
                            val profileUpdates = userProfileChangeRequest {
                                displayName =
                                    "${fistName.text.toString()} ${lastname.text.toString()}"
                            }
                            user!!.updateProfile(profileUpdates).addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Log.d(TAG, "Updated profile.")
                                }
                            }
                            Toast.makeText(this,"User registered.",Toast.LENGTH_SHORT).show()
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(
                                this, "Authentication failed.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }
    }


}