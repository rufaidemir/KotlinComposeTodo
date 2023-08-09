package com.rufaidemir.examplecompose.presentation

import com.rufaidemir.examplecompose.model.TodoItem

data class TodoListState(
    var todoList: List<TodoItem> = emptyList(),
)