package com.example.owd.data.expenses

import kotlinx.coroutines.flow.Flow

class ExpensesOfflineRepository(private val expensesDao: ExpensesDao): ExpensesRepository {
    override suspend fun insert(expense: Expense) : Long = expensesDao.insert(expense);

    override suspend fun update(expense: Expense) = expensesDao.update(expense);

    override suspend fun delete(expense: Expense) = expensesDao.delete(expense)

    override fun getAllExpenses(groupId: Int): Flow<List<Expense>> = expensesDao.getAllExpenses(groupId)

}