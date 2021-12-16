package com.example.zivotot_e_krug.ui.users.adult

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.zivotot_e_krug.R

class AdultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.adult_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, TaskFragment.newInstance())
                .commitNow()
        }
    }
}