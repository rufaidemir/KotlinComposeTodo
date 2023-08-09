package com.rufaidemir.examplecompose.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.rufaidemir.examplecompose.model.TodoItem
import com.rufaidemir.examplecompose.services.TodoDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch

class TodoItemViewModel(application: Application):BaseViewModel(application) {

    var mutTodoItems = MutableLiveData<List<TodoItem>>()
    var mutCurrentItem: MutableLiveData<TodoItem> = MutableLiveData<TodoItem>()


    fun getAllItems() {
        launch {
            val dao = TodoDatabase(getApplication()).todoDao()
            mutTodoItems.value = dao.getAllItems()
        }
    }
    fun getItem(id:Int){
        launch {
            val dao = TodoDatabase(getApplication()).todoDao()
            mutCurrentItem.value=dao.getItem(id)
            getAllItems()
        }
    }

    fun editItem(newItem:TodoItem){
        launch {
            val dao = TodoDatabase(getApplication()).todoDao()
            dao.updateItem(newItem)
            mutCurrentItem.value = newItem
            getAllItems()
        }
    }

    fun deleteItem(item: TodoItem){
        launch {
            val dao = TodoDatabase(getApplication()).todoDao()
            dao.deleteItem(item)
            getAllItems()
        }
    }
    fun addItem(item:TodoItem){
        launch {
            val dao = TodoDatabase(getApplication()).todoDao()
            dao.addItem(item)
            getAllItems()
        }
    }


}