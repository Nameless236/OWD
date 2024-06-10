package com.example.owd.data.expenses

import kotlinx.coroutines.flow.Flow

interface ExpensesRepository {
    suspend fun insert(expense: Expense) : Long

    suspend fun update(expense: Expense)

    suspend fun delete(expense: Expense)

    fun getAllExpenses(groupId: Int): Flow<List<Expense>>
}