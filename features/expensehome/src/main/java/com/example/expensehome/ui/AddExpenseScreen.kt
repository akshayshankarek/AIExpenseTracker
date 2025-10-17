package com.example.expensehome.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun AddExpenseScreen(
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(6.dp)
    ) {
        Text("Add Expenses", style = MaterialTheme.typography.headlineMedium)

    }
}