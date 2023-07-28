package com.rufaidemir.examplecompose.services

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rufaidemir.examplecompose.model.TodoItem

@Database(entities = [TodoItem::class], version = 1)
abstract class TodoDatabase:RoomDatabase() {

    abstract fun todoDao(): TodoItemDAO

    companion object{
        @Volatile private var instance : TodoDatabase? =null

        private val lock = Any()
        operator  fun invoke(context: Context) = instance?: synchronized(lock){
            instance?: createDB(context).also{
                instance = it
            }
        }

        private fun createDB(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            TodoDatabase::class.java,
            "TodoDatabase"
        ).build()
    }
}