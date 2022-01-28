package com.example.zivotot_e_krug.ui.users.volunteer.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zivotot_e_krug.R
import com.example.zivotot_e_krug.ui.users.adult.AdultViewModel
import com.example.zivotot_e_krug.ui.users.adult.adapters.TaskAdapter
import com.example.zivotot_e_krug.ui.users.adult.adapters.VolunteerAdapter
import com.example.zivotot_e_krug.ui.users.volunteer.VolunteerViewModel


class AllTaskFragment : Fragment() {

    private val mAdapter by lazy{ VolunteerAdapter() }
    private lateinit var viewModel: VolunteerViewModel
    private lateinit var mView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[VolunteerViewModel::class.java]
        viewModel.addDataListener()
        mView = inflater.inflate(R.layout.fragment_all_task_volunteer, container, false)
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
        mView.findViewById<RecyclerView>(R.id.recyclerViewVolunteer).adapter = mAdapter
        mView.findViewById<RecyclerView>(R.id.recyclerViewVolunteer).layoutManager = LinearLayoutManager(requireContext())
    }

}