package com.fame.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fame.model.TodoModel

@Database(entities = arrayOf(TodoModel::class), version = 1, exportSchema = false)
abstract class DataBase : RoomDatabase() {
    abstract fun dao(): TodoDao

     companion object {
        @Volatile
        private var instance: DataBase? = null

        operator fun invoke(context: Context): DataBase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
         }
        private fun buildDatabase(context: Context): DataBase {
            return Room.databaseBuilder(context, DataBase::class.java, "fame_db.db")
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}