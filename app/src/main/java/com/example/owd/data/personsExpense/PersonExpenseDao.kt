package com.example.owd.data.personsExpense

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.owd.data.persons.Person
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for accessing and manipulating [PersonExpense] entities in the database.
 */
@Dao
interface PersonExpenseDao {
    /**
     * Inserts a new [PersonExpense] entity into the database.
     *
     * @param personExpense The [PersonExpense] entity to be inserted.
     */
    @Insert
    suspend fun insert(personExpense: PersonExpense)

    /**
     * Updates an existing [PersonExpense] entity in the database.
     *
     * @param personExpense The [PersonExpense] entity to be updated.
     */
    @Update
    suspend fun update(personExpense: PersonExpense)

    /**
     * Deletes a [PersonExpense] entity from the database.
     *
     * @param personExpense The [PersonExpense] entity to be deleted.
     */
    @Delete
    suspend fun delete(personExpense: PersonExpense)

    /**
     * Retrieves a list of persons associated with a specific expense from the database.
     *
     * @param expenseId The ID of the expense.
     * @return A [Flow] emitting a list of [Person] entities associated with the specified expense.
     */
    @Query("SELECT * from persons WHERE id in (SELECT personId from personExpense WHERE expenseId = :expenseId)")
    fun getPersons(expenseId: Int): Flow<List<Person?>>

    /**
     * Retrieves the total amount paid by a specific person from the database.
     *
     * @param pId The ID of the person.
     * @return A [Flow] emitting the total amount paid by the specified person.
     */
    @Query("SELECT sum(amount) from personExpense WHERE personId = :pId")
    fun getAmmountForPerson(pId: Int): Flow<Float>
}
