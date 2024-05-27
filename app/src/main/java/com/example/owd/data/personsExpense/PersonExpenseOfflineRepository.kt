package com.example.owd.data.personsExpense

import com.example.owd.data.persons.Person
import kotlinx.coroutines.flow.Flow

class PersonExpenseOfflineRepository(private val personExpenseDao: PersonExpenseDao) : PersonExpenseRepository {
    override suspend fun insert(personExpense: PersonExpense) = personExpenseDao.insert(personExpense)

    override suspend fun update(personExpense: PersonExpense) = personExpenseDao.update(personExpense)

    override suspend fun delete(personExpense: PersonExpense) = personExpenseDao.delete(personExpense)

    override fun getPersons(expenseId: Int): Flow<List<Person?>> = personExpenseDao.getPersons(expenseId)

    override fun getAmmountForPerson(pId: Int): Flow<Float> = personExpenseDao.getAmmountForPerson(pId)

}