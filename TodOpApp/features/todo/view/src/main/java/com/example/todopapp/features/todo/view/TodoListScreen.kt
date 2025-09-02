package com.example.todopapp.features.todo.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.todopapp.features.todo.viewmodel.TodoListViewModel
import com.example.todopapp.todo.domain.models.Todo
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(
    onNavigateToAddTodo: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TodoListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Todo List") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToAddTodo
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Todo"
                )
            }
        },
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                uiState.todos.isEmpty() -> {
                    Text(
                        text = "No todos yet!\nTap + to add your first todo",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                else -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(
                            items = uiState.todos,
                            key = { it.id }
                        ) { todo ->
                            TodoItem(
                                todo = todo,
                                onToggleCompletion = { viewModel.toggleTodoCompletion(todo.id) },
                                onDelete = { viewModel.deleteTodo(todo) }
                            )
                        }
                    }
                }
            }
        }

        uiState.errorMessage?.let { error ->
            LaunchedEffect(error) {
                // Handle error display (could be a snackbar)
                viewModel.clearError()
            }
        }
    }
}

@Preview
@Composable
private fun TodoListScreenPreview() {
    MaterialTheme {
        TodoListScreen(
            onNavigateToAddTodo = {}
        )
    }
}