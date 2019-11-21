package com.fame.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_table")
data class TodoModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var title: String? = null,
    var description: String? = null,
    var date: String? = null,
    var time: String? = null
)