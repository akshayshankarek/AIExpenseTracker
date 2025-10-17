package com.example.expensehome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.Expense
import com.example.repository.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ExpenseViewModel @Inject constructor(
    private val expenseRepository: ExpenseRepository
) : ViewModel() {


    val expenses = expenseRepository.getAllExpenses().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
    )


    fun addExpense(title: String, amount: Double, category: String) {
        val expense = Expense(title = title, amount = amount, category = category)
        viewModelScope.launch {
            expenseRepository.insertExpense(expense)
        }
    }

    fun deleteExpense(expense: Expense) {
        viewModelScope.launch {
            expenseRepository.deleteExpense(expense)
        }
    }

}