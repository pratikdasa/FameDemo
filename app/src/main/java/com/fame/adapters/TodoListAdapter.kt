package com.fame.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fame.R
import com.fame.listeners.RvListeners
import com.fame.model.TodoModel
import kotlinx.android.synthetic.main.todo_list_item.view.*

class TodoListAdapter(val rvListeners: RvListeners) : RecyclerView.Adapter<TodoListAdapter.Holder>() {

    var todoList = ArrayList<TodoModel>()
    fun saveUser(todoModelList: ArrayList<TodoModel>) {
        this.todoList = todoModelList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        return Holder((layoutInflater.inflate(R.layout.todo_list_item, parent, false)))
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val user = todoList.get(position)
        holder.txtTitle.text = user.title
        holder.txtDescription.text = user.description
        holder.txtDate.text = user.date
        holder.txtTime.text = user.time
        holder.imgDelete.setOnClickListener {
            rvListeners.onItemClick(holder.imgDelete, user)
        }
        holder.imgEdit.setOnClickListener {
            rvListeners.onItemClick(holder.imgEdit, user)
        }
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgDelete = itemView.imgDelete
        val imgEdit = itemView.imgEdit
        val txtTitle = itemView.txtTitle
        val txtDescription = itemView.txtDescription
        val txtDate = itemView.txtDate
        val txtTime = itemView.txtTime

    }

}