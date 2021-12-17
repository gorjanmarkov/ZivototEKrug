package com.example.zivotot_e_krug.ui.users.adult

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.zivotot_e_krug.R

class ReviewFragment : Fragment() {

    private lateinit var viewModel: AdultViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(AdultViewModel::class.java)

        return inflater.inflate(R.layout.review_task_fragment, container, false)
    }
}