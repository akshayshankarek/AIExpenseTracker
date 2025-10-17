package com.example.expensehome.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.expensehome.ExpenseViewModel

@Composable
internal fun ExpenseListScreen(
    viewModel: ExpenseViewModel = hiltViewModel(),
    onAddClick: () -> Unit
) {
    val expenses by viewModel.expenses.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick
            ) {
                Text("+")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(6.dp)
        ) {
            Text("Expenses", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn {
                items(expenses.size) {
                    index -> val item = expenses[index]
                    Text(item.title)
                }
            }
        }

    }
}