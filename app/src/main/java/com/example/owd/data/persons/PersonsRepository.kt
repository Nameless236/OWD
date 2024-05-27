package com.example.owd.data.persons

import kotlinx.coroutines.flow.Flow

interface PersonsRepository {
    suspend fun insert(person: Person)

    suspend fun update(person: Person)

    suspend fun delete(person: Person)

    fun getPerson(groupId: Int): Flow<Person?>

    fun getAllPersons(): Flow<List<Person?>>
}