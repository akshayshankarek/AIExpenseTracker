package com.example.repository

import com.example.data.local.dao.ExpenseDao
import com.example.data.model.Expense
import com.example.data.remote.GeminiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExpenseRepository @Inject constructor(
    private val expenseDao: ExpenseDao,
    private val geminiService: GeminiService
) {
    fun getAllExpenses(): Flow<List<Expense>> =
        expenseDao.getAllExpenses()

    suspend fun insertExpense(expense: Expense) {
        expenseDao.insertExpense(expense)
    }

    suspend fun deleteExpense(expense: Expense) {
        expenseDao.deleteExpense(expense)
    }

    suspend fun getSuggestedCategory(title: String): String {
        return geminiService.suggestCategory(title)
    }
}