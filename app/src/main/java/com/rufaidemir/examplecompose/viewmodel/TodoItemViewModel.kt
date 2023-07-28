package com.rufaidemir.examplecompose.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.rufaidemir.examplecompose.model.TodoItem
import com.rufaidemir.examplecompose.services.TodoDatabase
import kotlinx.coroutines.launch

class TodoItemViewModel(application: Application):BaseViewModel(application) {

    var mutTodoItems = MutableLiveData<List<TodoItem>>()
    var mutCurrentItem = MutableLiveData<TodoItem>()

    fun getAllItems(){
        launch {
            val dao = TodoDatabase(getApplication()).todoDao()
            mutTodoItems.value=dao.getAllItems()
        }
    }
    fun getItem(id:Int){
        launch {
            val dao = TodoDatabase(getApplication()).todoDao()
            mutCurrentItem.value=dao.getItem(id)
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