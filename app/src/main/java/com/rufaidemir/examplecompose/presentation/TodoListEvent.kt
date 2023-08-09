package com.rufaidemir.examplecompose.presentation

sealed interface TodoListEvent{
    object OnAddNewTodoItem:TodoListEvent
    object OnDeleteTodoItem:TodoListEvent
    data class OnTitleChanged(val title:String):TodoListEvent
}