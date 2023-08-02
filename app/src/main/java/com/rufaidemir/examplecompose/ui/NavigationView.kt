package com.rufaidemir.examplecompose.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rufaidemir.examplecompose.ui.components.EditTodoItemScreen
import com.rufaidemir.examplecompose.ui.components.TodoList
import com.rufaidemir.examplecompose.viewmodel.TodoItemViewModel


@Composable
fun NavigationView(itemViewModel:TodoItemViewModel){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home" ){
        composable("home"){
            TodoList(navController,viewModel = itemViewModel)
        }
        composable("edit"){
            EditTodoItemScreen(navController, todoViewModel = itemViewModel)
        }
    }
}