package com.example.todoaster.fragments.list.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.todoaster.R
import com.example.todoaster.data.models.Priority
import com.example.todoaster.data.models.ToDoData
import com.example.todoaster.databinding.RowLayoutBinding

class ListAdapter(
    private val onItemClick: (item: ToDoData) -> Unit
) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    var dataList = emptyList<ToDoData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val todo = dataList[position]
        holder.bind(todo)
    }

    inner class ViewHolder(private val binding: RowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(toDoData: ToDoData) {
            with(binding) {
                tvTitleText.text = toDoData.title
                tvDescriptionText.text = toDoData.description
                clRowBackground.setOnClickListener {
                    onItemClick(toDoData)
                }
            }
            when (toDoData.priority) {
                Priority.High -> priorityIndicatorColor(R.color.red)
                Priority.Medium -> priorityIndicatorColor(R.color.yellow)
                Priority.Low -> priorityIndicatorColor(R.color.green)
            }
        }

        private fun priorityIndicatorColor(
            @ColorRes res: Int
        ) {
            binding.cvPriorityIndicator.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(
                    itemView.context,
                    res
                )
            )
        }
    }

    fun setData(toDoData: List<ToDoData>) {
        val toDoDiffUtil = ToDoDiffUtil(dataList, toDoData)
        val toDoDiffResult = DiffUtil.calculateDiff(toDoDiffUtil)
        dataList = toDoData
        toDoDiffResult.dispatchUpdatesTo(this)
    }
}