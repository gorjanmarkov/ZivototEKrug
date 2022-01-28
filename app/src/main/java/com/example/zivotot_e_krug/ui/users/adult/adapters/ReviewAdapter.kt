package com.example.zivotot_e_krug.ui.users.adult.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.zivotot_e_krug.DiffUtil.ReviewDiffUtil
import com.example.zivotot_e_krug.data.model.Review
import com.example.zivotot_e_krug.databinding.ReviewAdultLayoutBinding
import com.example.zivotot_e_krug.databinding.ReviewVolunteerLayoutBinding


class ReviewAdapter : RecyclerView.Adapter<ReviewAdapter.MyViewHolder>() {

    private var tasks = emptyList<Review>()

    class MyViewHolder(private val binding: ReviewAdultLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Review) {
            binding.properties = task
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ReviewAdultLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentTask = tasks[position]
        holder.bind(currentTask)
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    fun setData(newData: List<Review>) {
        val reviewDiffUtil = ReviewDiffUtil(tasks, newData)
        val diffUtilResult = DiffUtil.calculateDiff(reviewDiffUtil)
        tasks = newData
        diffUtilResult.dispatchUpdatesTo(this)
    }
}