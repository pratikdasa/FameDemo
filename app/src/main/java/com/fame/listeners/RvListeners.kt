package com.fame.listeners

import android.view.View
import com.fame.model.TodoModel

interface RvListeners {
    fun onItemClick(view: View, todoModel: TodoModel)
}