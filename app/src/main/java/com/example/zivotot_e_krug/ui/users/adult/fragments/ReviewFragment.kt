package com.example.zivotot_e_krug.ui.users.adult.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zivotot_e_krug.R
import com.example.zivotot_e_krug.ui.users.adult.AdultViewModel
import com.example.zivotot_e_krug.ui.users.adult.adapters.ReviewAdapter


class ReviewFragment : Fragment() {


    private lateinit var viewModel: AdultViewModel
    private val mAdapter by lazy { ReviewAdapter() }
    private lateinit var mView: View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[AdultViewModel::class.java]
        viewModel.addReviewListener()
        mView = inflater.inflate(R.layout.review_task_fragment, container, false)
        setupRecyclerView()
        requestData()

        return mView
    }

    private fun requestData() {
        viewModel._review.observe(viewLifecycleOwner, {
            mAdapter.setData(it)
        })
    }

    private fun setupRecyclerView() {

        mView.findViewById<RecyclerView>(R.id.reviewRecyclerView).adapter = mAdapter

        mView.findViewById<RecyclerView>(R.id.reviewRecyclerView).layoutManager =
            LinearLayoutManager(requireContext())

    }
}