package com.fame.repository

import com.fame.db.TodoDao
import com.fame.model.TodoModel

class MainRepository(val todoDao: TodoDao) {
    suspend fun selectTodoList() = todoDao.getTodoList()
    suspend fun saveAndSelect(todoModel: TodoModel) = todoDao.insertAndSelect(todoModel)
    suspend fun updateAndSelect(todoModel: TodoModel) = todoDao.updateAndSelect(todoModel)
    suspend fun deleteAndSelect(todoModel: TodoModel)=todoDao.deleteAndSelect(todoModel)


}