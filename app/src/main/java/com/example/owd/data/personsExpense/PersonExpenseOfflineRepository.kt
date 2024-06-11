package com.example.owd.data.personsExpense

import com.example.owd.data.persons.Person
import kotlinx.coroutines.flow.Flow

/**
 * Repository responsible for managing [PersonExpense] entities offline.
 *
 * @property personExpenseDao The data access object (DAO) for interacting with [PersonExpense] entities.
 */
class PersonExpenseOfflineRepository(private val personExpenseDao: PersonExpenseDao) : PersonExpenseRepository {
    /**
     * Inserts a new [PersonExpense] entity into the database.
     *
     * @param personExpense The [PersonExpense] entity to be inserted.
     */
    override suspend fun insert(personExpense: PersonExpense) = personExpenseDao.insert(personExpense)

    /**
     * Updates an existing [PersonExpense] entity in the database.
     *
     * @param personExpense The [PersonExpense] entity to be updated.
     */
    override suspend fun update(personExpense: PersonExpense) = personExpenseDao.update(personExpense)

    /**
     * Deletes a [PersonExpense] entity from the database.
     *
     * @param personExpense The [PersonExpense] entity to be deleted.
     */
    override suspend fun delete(personExpense: PersonExpense) = personExpenseDao.delete(personExpense)

    /**
     * Retrieves a list of persons associated with a specific expense from the database.
     *
     * @param expenseId The ID of the expense.
     * @return A [Flow] emitting a list of [Person] entities associated with the specified expense.
     */
    override fun getPersons(expenseId: Int): Flow<List<Person?>> = personExpenseDao.getPersons(expenseId)

    /**
     * Retrieves the total amount paid by a specific person from the database.
     *
     * @param pId The ID of the person.
     * @return A [Flow] emitting the total amount paid by the specified person.
     */
    override fun getAmmountForPerson(pId: Int): Flow<Float> = personExpenseDao.getAmmountForPerson(pId)
}
