package com.example.owd.data.personsExpense

import com.example.owd.data.persons.Person
import kotlinx.coroutines.flow.Flow

interface PersonExpenseRepository {
    suspend fun insert(personExpense: PersonExpense)

    suspend fun update(personExpense: PersonExpense)

    suspend fun delete(personExpense: PersonExpense)

    fun getPersons(expenseId: Int): Flow<List<Person?>>

    fun getAmmountForPerson(pId: Int): Flow<Float>
}