package com.example.zivotot_e_krug.ui.users.volunteer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.zivotot_e_krug.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class VolunteerActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_volunteer)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragmentVolunteer) as NavHostFragment
        navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.allTaskFragment,
                R.id.getTaskFragment,
                R.id.reviewFragmentVolunteer
            )
        )
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationViewVolunteer)
        bottomNavigationView.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}