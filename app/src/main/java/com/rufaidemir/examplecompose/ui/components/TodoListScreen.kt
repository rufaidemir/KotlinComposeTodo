package com.rufaidemir.examplecompose.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.rufaidemir.examplecompose.model.TodoItem
import com.rufaidemir.examplecompose.presentation.TodoListEvent
import com.rufaidemir.examplecompose.presentation.TodoListState
import com.rufaidemir.examplecompose.viewmodel.TodoItemViewModel
import com.rufaidemir.examplecompose.viewmodel.TodoListViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoList(navController: NavController, todoListViewModel : TodoListViewModel = viewModel(), onEvent:(TodoListEvent)->Unit ) {
    val snackbarHostState = remember { SnackbarHostState() }
    val todoListState = todoListViewModel.state.collectAsState()
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

        ) {
            itemsIndexed(todoListState.value.todoList) { _, totoItem ->

                val state = rememberDismissState(
                    confirmValueChange = {
                        var ret = true
                        if (it==DismissValue.DismissedToStart){
                            todoListState.apply {
                                todoListState.value.todoList.takeLast(1)
                            }
                            println("=====================ob5je siniliniy6rooooooo")
                        }else if (it==DismissValue.DismissedToEnd){
                            navController.navigate("edit")
                            ret=true
                        }
                        ret

                    }
                )

                SwipeToDismiss(state = state, background ={
                    val color = when(state.dismissDirection){
                        DismissDirection.StartToEnd -> Color.Transparent
                        DismissDirection.EndToStart -> Color.Transparent
                        null -> Color.Transparent
                    }

                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .background(color)
                        .border(8.dp, Color.Transparent)
                        .padding(8.dp)){
                        Icon( Icons.Default.Delete, contentDescription ="",
                            tint = Color.Red,
                            modifier = Modifier.align(Alignment.CenterEnd)
                        )
                    }
                },
                dismissContent = {
                    TodoItemView(
                        navController = navController,
                        item = totoItem,
                        modifyItem = {
                        })
                })

            }

        }
    }


}


