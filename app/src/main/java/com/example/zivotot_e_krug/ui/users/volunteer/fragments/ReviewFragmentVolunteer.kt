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
import com.example.zivotot_e_krug.ui.users.adult.adapters.ReviewAdapter
import com.example.zivotot_e_krug.ui.users.volunteer.VolunteerViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ReviewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReviewFragment : Fragment() {

    private lateinit var viewModel: VolunteerViewModel
    private val mAdapter by lazy { ReviewAdapter() }
    private lateinit var mView: View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[VolunteerViewModel::class.java]
        viewModel.addReviewListener()
        mView = inflater.inflate(R.layout.fragment_review_volunteer, container, false)
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

        mView.findViewById<RecyclerView>(R.id.reviewAdultRecyclerView).adapter = mAdapter

        mView.findViewById<RecyclerView>(R.id.reviewAdultRecyclerView).layoutManager =
            LinearLayoutManager(requireContext())

    }

}