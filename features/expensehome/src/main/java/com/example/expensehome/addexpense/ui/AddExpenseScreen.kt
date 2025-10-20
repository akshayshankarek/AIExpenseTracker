package com.example.expensehome.addexpense.ui


import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.camerax.ReceiptScannerScreen
import com.example.design.theme.AIExpenseTrackerTheme
import com.example.expensehome.addexpense.AddExpenseContract
import com.example.expensehome.addexpense.AddExpenseViewModel

@Composable
internal fun AddExpenseScreen(
    viewModel: AddExpenseViewModel = hiltViewModel(),
    onSaveSuccess: () -> Unit
) {
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()
    when (viewState.addExpenseType) {
        AddExpenseContract.ADD_EXPENSE_TYPE.DEFAULT -> {
            AddExpenseHome(
                viewState,
                onSave = {
                    viewModel.saveExpense()
                    onSaveSuccess()
                },
                onTitleChange = { viewModel.onTitleChange(it) },
                onAmountChange = { viewModel.onAmountChange(it) },
                onCategoryChange = { viewModel.onCategoryChange(it) },
                onAiCategorySearch = { viewModel.onAiCategorySearch() },
                isSuggestionLoading = viewState.isSuggestionLoading,
                onScanReceipt = { viewModel.onScanReceipt() }
            )
        }

        AddExpenseContract.ADD_EXPENSE_TYPE.SCAN -> {
            ReceiptScannerScreen(onImageCaptured = { viewModel.onImageCaptured(it) })
        }
    }

}

@Composable
internal fun AddExpenseHome(
    viewState: AddExpenseContract.ViewState,
    onSave: () -> Unit,
    onTitleChange: (String) -> Unit,
    onAmountChange: (String) -> Unit,
    onCategoryChange: (String) -> Unit,
    onAiCategorySearch: () -> Unit,
    isSuggestionLoading: Boolean,
    onScanReceipt: () -> Unit
) {
    Box() {
        if (isSuggestionLoading) {
            AiPulseLoader()
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text("Add Expenses", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = viewState.title,
                onValueChange = { onTitleChange(it) },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black
                )
            )

            OutlinedTextField(
                value = viewState.amount,
                onValueChange = { onAmountChange(it) },
                label = { Text("Amount") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black
                )
            )

            OutlinedTextField(
                value = viewState.category,
                onValueChange = { onCategoryChange(it) },
                label = { Text("Category") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            AiSuggestButton(
                onClick = onAiCategorySearch,
                isLoading = isSuggestionLoading
            )

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    onSave()
                },
                enabled = !viewState.isSaving,
                modifier = Modifier.align(Alignment.End)
            ) {
                Icon(Icons.Default.Done, contentDescription = null)
                Spacer(modifier = Modifier.width(4.dp))
                Text("Save")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    onScanReceipt()
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Icon(Icons.Default.AddCircle, contentDescription = null)
                Spacer(modifier = Modifier.width(4.dp))
                Text("Scan Receipt")
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun AddExpenseScreenPreview() {
    AIExpenseTrackerTheme {
        AddExpenseHome(
            viewState = AddExpenseContract.ViewState.Default,
            onSave = {},
            onTitleChange = {},
            onAmountChange = {},
            onCategoryChange = {},
            isSuggestionLoading = true,
            onAiCategorySearch = {},
            onScanReceipt = {})
    }

}