package com.example.zivotot_e_krug.ui.users.adult.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.zivotot_e_krug.DiffUtil.TaskDiffUtil
import com.example.zivotot_e_krug.data.model.TaskName
import com.example.zivotot_e_krug.data.model.TaskProperties
import com.example.zivotot_e_krug.databinding.TaskLayoutBinding


class TaskAdapter : RecyclerView.Adapter<TaskAdapter.MyViewHolder>() {

    private var tasks = emptyList<TaskProperties>()

    class MyViewHolder(private val binding: TaskLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(task: TaskProperties) {
            binding.properties = task
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = TaskLayoutBinding.inflate(layoutInflater, parent, false)
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

    fun setData(newData: TaskName) {
        val taskDiffUtil = TaskDiffUtil(tasks, newData.name)
        val diffUtilResult = DiffUtil.calculateDiff(taskDiffUtil)
        tasks = newData.name
        diffUtilResult.dispatchUpdatesTo(this)

    }
}