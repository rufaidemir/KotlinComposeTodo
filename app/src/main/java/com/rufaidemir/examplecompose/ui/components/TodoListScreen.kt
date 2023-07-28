package com.rufaidemir.examplecompose.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rufaidemir.examplecompose.viewmodel.TodoItemViewModel


@Composable
fun TodoList(viewModel: TodoItemViewModel) {
    // viewModel.mutTodoItems StateFlow'ını Compose tarafında State olarak sakla
    val todoListState = viewModel.mutTodoItems.collectAsState(emptyList())
    println("=================+++${todoListState.value}")
    LaunchedEffect(Unit) {
        viewModel.getAllItems()
    }

    // println("============================== ${todoListState}")

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(todoListState.value.size) { todoItemIndex ->
            val item = todoListState.value[todoItemIndex]
            TodoItem(item = item)
        }
    }
}