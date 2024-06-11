package com.example.owd.data.expenses

import kotlinx.coroutines.flow.Flow

/**
 * Repository implementation for handling expense data operations offline.
 * Acts as an intermediary between the ViewModel and the DAO, providing methods
 * for inserting, updating, deleting, and retrieving expenses from the database.
 *
 * @property expensesDao The Data Access Object (DAO) for expense entities.
 */
class ExpensesOfflineRepository(private val expensesDao: ExpensesDao) : ExpensesRepository {

    /**
     * Inserts a new expense into the database.
     *
     * @param expense The expense entity to insert.
     * @return The ID of the newly inserted expense.
     */
    override suspend fun insert(expense: Expense): Long = expensesDao.insert(expense)

    /**
     * Updates an existing expense in the database.
     *
     * @param expense The expense entity to update.
     */
    override suspend fun update(expense: Expense) = expensesDao.update(expense)

    /**
     * Deletes an expense from the database.
     *
     * @param expense The expense entity to delete.
     */
    override suspend fun delete(expense: Expense) = expensesDao.delete(expense)

    /**
     * Retrieves all expenses belonging to a specific group from the database.
     *
     * @param groupId The ID of the group.
     * @return A flow representing a list of expenses associated with the specified group.
     */
    override fun getAllExpenses(groupId: Int): Flow<List<Expense>> = expensesDao.getAllExpenses(groupId)
}
