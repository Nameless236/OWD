package com.example.owd.data.personsExpense

import com.example.owd.data.persons.Person
import kotlinx.coroutines.flow.Flow

/**
 * Interface defining the contract for managing [PersonExpense] entities.
 */
interface PersonExpenseRepository {
    /**
     * Inserts a new [PersonExpense] entity.
     *
     * @param personExpense The [PersonExpense] entity to be inserted.
     */
    suspend fun insert(personExpense: PersonExpense)

    /**
     * Updates an existing [PersonExpense] entity.
     *
     * @param personExpense The [PersonExpense] entity to be updated.
     */
    suspend fun update(personExpense: PersonExpense)

    /**
     * Deletes a [PersonExpense] entity.
     *
     * @param personExpense The [PersonExpense] entity to be deleted.
     */
    suspend fun delete(personExpense: PersonExpense)

    /**
     * Retrieves a list of persons associated with a specific expense.
     *
     * @param expenseId The ID of the expense.
     * @return A [Flow] emitting a list of [Person] entities associated with the specified expense.
     */
    fun getPersons(expenseId: Int): Flow<List<Person?>>

    /**
     * Retrieves the total amount paid by a specific person.
     *
     * @param pId The ID of the person.
     * @return A [Flow] emitting the total amount paid by the specified person.
     */
    fun getAmmountForPerson(pId: Int): Flow<Float>
}
