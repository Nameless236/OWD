package com.example.owd.data.persons

import kotlinx.coroutines.flow.Flow

class PersonsOfflineRepository(private val personDao: PersonDao) : PersonsRepository{
    override suspend fun insert(person: Person) = personDao.insert(person)

    override suspend fun update(person: Person) = personDao.update(person)

    override suspend fun delete(person: Person) = personDao.delete(person)

    override fun getPerson(groupId: Int): Flow<Person?> = personDao.getPerson(groupId)

    override fun getAllPersons(): Flow<List<Person?>> = personDao.getAllPersons()

    override fun getPersonsGroup(groupId: Int): Flow<List<Person>> = personDao.getPersonsGroup(groupId)
}