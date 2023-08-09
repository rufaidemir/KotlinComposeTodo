package com.rufaidemir.examplecompose.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import com.rufaidemir.examplecompose.model.TodoItem
import com.rufaidemir.examplecompose.presentation.TodoListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TodoListViewModel:ViewModel() {

    private val _state = MutableStateFlow(TodoListState(
        todoList = tempTodos
    ))

    val state = _state.asStateFlow()

    var newTodo: TodoItem? by mutableStateOf(null)
        private set



    fun getAllItems(){

    }

}

private val tempTodos = (1..20).map {
    TodoItem(
        title = "Title$it",
        hasTag = false,
        hasTime = false,
        hasInterval = false,
        hasColor = false,
        color = Color.Red.toArgb(),
        time = System.currentTimeMillis()
    )
}