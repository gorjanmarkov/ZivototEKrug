package com.example.zivotot_e_krug.ui.login

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.zivotot_e_krug.databinding.ActivityLoginBinding
import com.example.zivotot_e_krug.R
import com.example.zivotot_e_krug.ui.register.RegisterActivity
import com.example.zivotot_e_krug.ui.users.adult.AdultActivity
import com.example.zivotot_e_krug.ui.users.volunteer.VolunteerActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

@InternalCoroutinesApi
class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var activityIntent: Intent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        database = Firebase.database.reference
        setContentView(binding.root)
        auth = Firebase.auth
        auth.signOut()
        supportActionBar!!.hide()
        val username = binding.username
        val password = binding.password
        val login = binding.login
        val loading = binding.loading
        val register = binding.register
        val registerIntent = Intent(this, RegisterActivity::class.java)


        register!!.setOnClickListener {

            startActivity(registerIntent)
        }

        loginViewModel = ViewModelProvider(this, LoginViewModelFactory())
            .get(LoginViewModel::class.java)

        if (loginViewModel.loggedIn.value == true) {
            startActivity(activityIntent)
        }


        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })

        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                lifecycleScope.launch {
                    updateUiWithUser(loginResult.success)
                }

                setResult(Activity.RESULT_OK)
                finish()
            }

        })


        username.afterTextChanged {
            loginViewModel.loginDataChanged(
                username.text.toString(),
                password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
                )
            }


            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                       suspend{ loginViewModel.login(
                           username.text.toString(),
                           password.text.toString()
                       )}
                }
                false
            }


        }

        login.setOnClickListener {

            loading.visibility = View.VISIBLE
             loginViewModel.login(username.text.toString(), password.text.toString())

        }
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome)
        //TODO : initiate successful logged in experience
        Toast.makeText(
            this,
            "$welcome ${model.displayName}",
            Toast.LENGTH_LONG
        ).show()
        activityIntent = when (model.type) {
            "Adult" -> {
                Intent(this, AdultActivity::class.java)
            }
            "Volunteer" -> {
                Intent(this, VolunteerActivity::class.java)
            }
            else -> {
                Intent(this, RegisterActivity::class.java)
            }
        }
        startActivity(activityIntent)
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}