package com.example.zivotot_e_krug.DiffUtil

import androidx.recyclerview.widget.DiffUtil
import com.example.zivotot_e_krug.data.model.TaskProperties

class TaskDiffUtil(
    private val oldList: List<TaskProperties>,
    private val newList: List<TaskProperties>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}