package com.rufaidemir.examplecompose.services

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.rufaidemir.examplecompose.model.TodoItem

@Dao
interface TodoItemDAO {
    @Insert
    suspend fun addItem(vararg item: TodoItem):List<Long>

    @Query("SELECT * FROM todotable")
    suspend fun getAllItems(): List<TodoItem>

    @Query("SELECT * FROM todotable WHERE uuid = :id")
    suspend fun getItem(id:Int):TodoItem

    @Delete
    suspend fun deleteItem(item: TodoItem)


    @Update
    suspend fun updateItem(item: TodoItem)

    @Query("DELETE FROM todotable")
    suspend fun deleteAllItems()
}