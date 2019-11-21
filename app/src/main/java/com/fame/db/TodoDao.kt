package com.fame.db

import androidx.room.*
import com.fame.model.TodoModel

@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveTodo(todoModel: TodoModel)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(todoModel: TodoModel)

    @Delete
    suspend fun delete(todoModel: TodoModel)

    @Query("SELECT * FROM todo_table")
    suspend fun getTodoList(): List<TodoModel>

    @Transaction
    suspend fun deleteAndSelect(todoModel: TodoModel): List<TodoModel> {
        delete(todoModel)
        return getTodoList()
    }

    @Transaction
    suspend fun insertAndSelect(todoModel: TodoModel): List<TodoModel> {
        saveTodo(todoModel)
        return getTodoList()
    }

    @Transaction
    suspend fun updateAndSelect(todoModel: TodoModel): List<TodoModel> {
        update(todoModel)
        return getTodoList()
    }


}