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

    private val _mutTodoItems = MutableStateFlow<List<TodoItem>>(emptyList())
    private val _mutCurrentItem = MutableStateFlow<TodoItem?>(null)

    var mutTodoItems = _mutTodoItems
    val mutCurrentItem: StateFlow<TodoItem?> = _mutCurrentItem

    fun getAllItems() {
        launch {
            val dao = TodoDatabase(getApplication()).todoDao()
            _mutTodoItems.value = dao.getAllItems()
        }
    }
    fun getItem(id:Int){
        launch {
            val dao = TodoDatabase(getApplication()).todoDao()
            _mutCurrentItem.value=dao.getItem(id)
        }
    }

    fun editItem(id:Int){
        launch {
            val dao = TodoDatabase(getApplication()).todoDao()
            dao.updateItem(dao.getItem(id))
        }
    }

    fun deleteItem(id:Int){
        launch {
            val dao = TodoDatabase(getApplication()).todoDao()
            dao.deleteItem(dao.getItem(id))
        }
    }
    fun addItem(item:TodoItem){
        launch {
            val dao = TodoDatabase(getApplication()).todoDao()
            dao.addItem(item)
        }
    }


}