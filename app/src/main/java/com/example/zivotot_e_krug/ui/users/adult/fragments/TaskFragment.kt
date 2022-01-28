package com.example.zivotot_e_krug.ui.users.adult.fragments

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zivotot_e_krug.R
import com.example.zivotot_e_krug.ui.users.adult.AdultViewModel
import com.example.zivotot_e_krug.ui.users.adult.adapters.TaskAdapter


class TaskFragment : Fragment() {

    private val mAdapter by lazy{ TaskAdapter() }
    private lateinit var viewModel: AdultViewModel
    private lateinit var mView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[AdultViewModel::class.java]
        viewModel.addDataListener()
        mView = inflater.inflate(R.layout.tasks_fragment, container, false)
        setupRecyclerView()
        requestData()
        return mView
    }
    private fun requestData(){
        viewModel._response.observe(viewLifecycleOwner,{
            mAdapter.setData(it)
        })
    }

    private fun setupRecyclerView(){
        mView.findViewById<RecyclerView>(R.id.recyclerView).adapter = mAdapter
        mView.findViewById<RecyclerView>(R.id.recyclerView).layoutManager = LinearLayoutManager(requireContext())
    }

}