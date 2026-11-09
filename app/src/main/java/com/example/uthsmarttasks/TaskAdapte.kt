package com.example.uthsmarttasks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(private var taskList: List<Task>) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rbTaskDone: RadioButton = itemView.findViewById(R.id.rbTaskDone)
        val tvTaskTitle: TextView = itemView.findViewById(R.id.tvTaskTitle)
        val tvTaskDetails: TextView = itemView.findViewById(R.id.tvTaskDetails)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentTask = taskList[position]

        holder.tvTaskTitle.text = currentTask.title
        holder.tvTaskDetails.text = "${currentTask.category} - ${currentTask.time}"
        holder.rbTaskDone.isChecked = currentTask.done
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    fun updateData(newTasks: List<Task>) {
        taskList = newTasks
        notifyDataSetChanged()
    }
}