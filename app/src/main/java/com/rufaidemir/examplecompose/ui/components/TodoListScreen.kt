package com.rufaidemir.examplecompose.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rufaidemir.examplecompose.viewmodel.TodoItemViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoList(navController: NavController,viewModel: TodoItemViewModel) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val todoListState by viewModel.mutTodoItems.collectAsState(emptyList())


    LaunchedEffect(Unit) {
        viewModel.getAllItems()
    }
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text("Kadere Bak Uygulamasi")
            })
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) { contentPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = contentPadding.calculateTopPadding()),
            contentPadding = PaddingValues(8.dp),
//            verticalArrangement = Arrangement.spacedBy(8.dp)

        ) {
            items(todoListState.size) { todoItemIndex ->
                val item = todoListState[todoItemIndex]

                TodoItemView(
                    navController=navController,
                    item = item,
                    modifyItem = {
                        viewModel.editItem(it)
                    },
                    onDeleteItem = {
                        scope.launch {
                            val result = snackbarHostState.showSnackbar(
                                "Not silindi!",
                                duration = SnackbarDuration.Short,
                                actionLabel = "Geri Al"
                            )
                            when (result) {
                                SnackbarResult.ActionPerformed -> {
                                    viewModel.mutTodoItems
                                    viewModel.getAllItems()
                                }
                                SnackbarResult.Dismissed -> {
                                    viewModel.deleteItem(item)
                                }
                            }
                        }
                    },
                setOnCurrentItem = {
                    viewModel.getItem(item.uuid)
                })
            }

        }
    }


}


