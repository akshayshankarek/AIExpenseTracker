package com.example.expensehome.addexpense

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.Expense
import com.example.repository.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class AddExpenseViewModel @Inject constructor(
    private val expenseRepository: ExpenseRepository
) : ViewModel() {
    private val mutableViewState = MutableStateFlow(AddExpenseContract.ViewState.Default)
    val viewState = mutableViewState.asStateFlow()

    fun onTitleChange(title: String) {
        mutableViewState.update { it.copy(title = title) }
    }

    fun onAmountChange(amount: String) {
        mutableViewState.update { it.copy(amount = amount) }
    }

    fun onCategoryChange(category: String) {
        mutableViewState.update { it.copy(category = category) }
    }

    fun saveExpense() {
        viewModelScope.launch {
            isSaving(true)
            val expense = Expense(
                category = mutableViewState.value.category,
                amount = mutableViewState.value.amount.toDoubleOrNull() ?: 0.0,
                title = mutableViewState.value.title
            )
            expenseRepository.insertExpense(expense)
            isSaving(false)
        }
    }

    private fun isSaving(isSaving: Boolean) {
        mutableViewState.update { it.copy(isSaving = isSaving) }
    }

    private fun isSuggestionLoading(isLoading: Boolean) {
        mutableViewState.update { it.copy(isSuggestionLoading = isLoading) }
    }

    internal fun onAiCategorySearch() {
        val titleText = mutableViewState.value.title
        if (titleText.isEmpty()) return

        viewModelScope.launch {
            isSuggestionLoading(true)
            try {
                val suggestion = expenseRepository.getSuggestedCategory(titleText)
                mutableViewState.update { it.copy(category = suggestion) }
            } catch (e: Exception) {
                mutableViewState.update { it.copy(category = "Others") }
            } finally {
                isSuggestionLoading(false)
            }
        }
    }
}