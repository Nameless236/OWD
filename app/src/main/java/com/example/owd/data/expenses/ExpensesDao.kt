package com.example.owd.data.expenses

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for accessing expense data from the database.
 * Provides methods for inserting, updating, deleting, and querying expense entities.
 */
@Dao
interface ExpensesDao {

    /**
     * Inserts a new expense into the database.
     *
     * @param expense The expense entity to insert.
     * @return The ID of the newly inserted expense.
     */
    @Insert
    suspend fun insert(expense: Expense): Long

    /**
     * Updates an existing expense in the database.
     *
     * @param expense The expense entity to update.
     */
    @Update
    suspend fun update(expense: Expense)

    /**
     * Deletes an expense from the database.
     *
     * @param expense The expense entity to delete.
     */
    @Delete
    suspend fun delete(expense: Expense)

    /**
     * Retrieves all expenses belonging to a specific group from the database.
     *
     * @param groupId The ID of the group.
     * @return A flow representing a list of expenses associated with the specified group.
     */
    @Query("SELECT * FROM expenses WHERE groupId = :groupId")
    fun getAllExpenses(groupId: Int): Flow<List<Expense>>
}
