package com.example.owd.data.persons

import kotlinx.coroutines.flow.Flow

/**
 * Offline repository implementation for managing persons.
 *
 * @property personDao The Data Access Object (DAO) for persons.
 */
class PersonsOfflineRepository(private val personDao: PersonDao) : PersonsRepository {
    /**
     * Inserts a person into the database.
     *
     * @param person The person to insert.
     */
    override suspend fun insert(person: Person) = personDao.insert(person)

    /**
     * Updates a person in the database.
     *
     * @param person The person to update.
     */
    override suspend fun update(person: Person) = personDao.update(person)

    /**
     * Deletes a person from the database.
     *
     * @param person The person to delete.
     */
    override suspend fun delete(person: Person) = personDao.delete(person)

    /**
     * Retrieves a person by its group ID.
     *
     * @param groupId The ID of the group.
     * @return A [Flow] emitting the person with the specified group ID.
     */
    override fun getPerson(groupId: Int): Flow<Person?> = personDao.getPerson(groupId)

    /**
     * Retrieves all persons in the database.
     *
     * @return A [Flow] emitting a list of all persons in the database.
     */
    override fun getAllPersons(): Flow<List<Person?>> = personDao.getAllPersons()

    /**
     * Retrieves all persons belonging to a specific group.
     *
     * @param groupId The ID of the group.
     * @return A [Flow] emitting a list of persons belonging to the specified group.
     */
    override fun getPersonsGroup(groupId: Int): Flow<List<Person>> = personDao.getPersonsGroup(groupId)
}
