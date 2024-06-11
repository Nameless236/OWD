package com.example.owd.data.expenses

import kotlinx.coroutines.flow.Flow

/**
 * Interface defining the contract for managing expense data operations.
 * Implemented by classes responsible for interacting with the database
 * and providing methods for inserting, updating, deleting, and retrieving expenses.
 */
interface ExpensesRepository {

    /**
     * Inserts a new expense into the database.
     *
     * @param expense The expense entity to insert.
     * @return The ID of the newly inserted expense.
     */
    suspend fun insert(expense: Expense): Long

    /**
     * Updates an existing expense in the database.
     *
     * @param expense The expense entity to update.
     */
    suspend fun update(expense: Expense)

    /**
     * Deletes an expense from the database.
     *
     * @param expense The expense entity to delete.
     */
    suspend fun delete(expense: Expense)

    /**
     * Retrieves all expenses belonging to a specific group from the database.
     *
     * @param groupId The ID of the group.
     * @return A flow representing a list of expenses associated with the specified group.
     */
    fun getAllExpenses(groupId: Int): Flow<List<Expense>>
}
