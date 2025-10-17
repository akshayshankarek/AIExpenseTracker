package com.example.data.di

import com.example.data.dao.ExpenseDao
import com.example.repository.ExpenseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideExpenseRepository(expenseDao: ExpenseDao): ExpenseRepository {
        return ExpenseRepository(expenseDao)
    }
}