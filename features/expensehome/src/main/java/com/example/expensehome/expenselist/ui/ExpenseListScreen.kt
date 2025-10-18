package com.example.expensehome.expenselist.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.data.model.Expense
import com.example.design.theme.AIExpenseTrackerTheme
import com.example.expensehome.expenselist.ExpenseViewModel

@Composable
internal fun ExpenseListScreen(
    viewModel: ExpenseViewModel = hiltViewModel(),
    onAddClick: () -> Unit
) {
    val expenses by viewModel.expenses.collectAsState()


    ExpenseListHome(expenses, onAddClick)

}

@Composable
fun ExpenseListHome(expenses: List<Expense>, onAddClick: () -> Unit) {
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
            if (expenses.isEmpty()) {
                Text(text = "No expenses yet", style = MaterialTheme.typography.bodyLarge)
            } else {
                Text("Expenses", style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(8.dp))
                LazyColumn {
                    items(expenses.size) { index ->
                        val item = expenses[index]
                        ExpenseItem(item)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExpenseListScreenPreview() {
    AIExpenseTrackerTheme {
        ExpenseListHome(
            expenses = listOf(
                Expense(title = "Netflix", category = "Entertainment", amount = 155.0),
                Expense(title = "Spotify", category = "Entertainment", amount = 155.0),
            ), onAddClick = {})
    }
}