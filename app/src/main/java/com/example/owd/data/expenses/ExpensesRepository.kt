package com.example.owd.data.expenses

import androidx.room.Update
import kotlinx.coroutines.flow.Flow

interface ExpensesRepository {
    suspend fun insert(expense: Expense)

    @Update
    suspend fun update(expense: Expense)

    suspend fun delete(expense: Expense)

    fun getAllExpenses(groupId: Int): Flow<Expense?>
}