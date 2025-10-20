package com.example.expensehome.addexpense

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.Expense
import com.example.data.remote.model.PromptType
import com.example.mltoolkit.ReceiptTextExtractor
import com.example.repository.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
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
                val suggestion = expenseRepository.getSuggestionFromGemini(
                    titleText,
                    promptType = PromptType.CATEGORY_SUGGESTION
                )
                mutableViewState.update { it.copy(category = suggestion) }
            } catch (e: Exception) {
                mutableViewState.update { it.copy(category = "Others") }
            } finally {
                isSuggestionLoading(false)
            }
        }
    }

    fun onScanReceipt() {
        mutableViewState.update { it.copy(addExpenseType = AddExpenseContract.ADD_EXPENSE_TYPE.SCAN) }
    }

    fun onImageCaptured(it: File) {
        viewModelScope.launch {
            val text = ReceiptTextExtractor.extractText(it)
            fillFromText(text)
            mutableViewState.update { it.copy(addExpenseType = AddExpenseContract.ADD_EXPENSE_TYPE.DEFAULT) }
        }
    }

    private fun fillFromText(text: String) {
        viewModelScope.launch {
            try {
                isSuggestionLoading(true)
                val response = expenseRepository.extractExpenseDetailsFromGemini(
                    text
                )
                mutableViewState.update {
                    it.copy(
                        amount = response.amount.toString(),
                        category = response.category.toString(),
                        title = response.title.toString()
                    )
                }
            } catch (e: Exception) {
                Log.e("AddExpenseViewModel", "fillFromText: ", e)
            } finally {
                isSuggestionLoading(false)
            }
        }

    }
}